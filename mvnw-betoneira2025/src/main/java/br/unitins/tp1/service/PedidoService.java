// src/main/java/br/unitins/tp1/service/PedidoService.java
package br.unitins.tp1.service;

import br.unitins.tp1.dto.PedidoRequestDTO;
import br.unitins.tp1.dto.PedidoResponseDTO;
import br.unitins.tp1.dto.ItemPedidoRequestDTO;
import br.unitins.tp1.exception.ServiceException;
import br.unitins.tp1.model.Cliente;
import br.unitins.tp1.model.Endereco; // Import Endereco
import br.unitins.tp1.model.ItemPedido;
import br.unitins.tp1.model.Pedido;
import br.unitins.tp1.repository.ClienteRepository;
import br.unitins.tp1.repository.EnderecoRepository; // Import EnderecoRepository
import br.unitins.tp1.repository.ItemPedidoRepository;
import br.unitins.tp1.repository.PedidoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PedidoService {

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    EnderecoRepository enderecoRepository; // Inject EnderecoRepository

    @Inject
    ItemPedidoRepository itemPedidoRepository;

    @Inject
    SecurityContext securityContext;

    @Transactional
    public PedidoResponseDTO create(PedidoRequestDTO dto) {
        String userEmail = securityContext.getUserPrincipal().getName();
        Cliente cliente = clienteRepository.findByEmail(userEmail);

        if (cliente == null) {
            throw new ServiceException("Cliente autenticado não encontrado.", Response.Status.UNAUTHORIZED);
        }

        // Validate and set delivery address
        Endereco enderecoEntrega = enderecoRepository.findById(dto.getIdEnderecoEntrega());
        if (enderecoEntrega == null) {
            throw new ServiceException("Endereço de entrega não encontrado.", Response.Status.NOT_FOUND);
        }
        // Ensure the delivery address belongs to the authenticated client (if a regular user)
        if (!securityContext.isUserInRole("ADMIN") && !enderecoEntrega.getCliente().getId().equals(cliente.getId())) {
             throw new ServiceException("Endereço de entrega não pertence ao cliente autenticado.", Response.Status.FORBIDDEN);
        }


        Pedido pedido = new Pedido();
        pedido.setDataDoPedido(LocalDateTime.now());
        pedido.setCliente(cliente); // Set the authenticated client to the order
        pedido.setEnderecoEntrega(enderecoEntrega); // Set the delivery address

        Double totalCalculado = 0.0;
        if (dto.getItens() != null && !dto.getItens().isEmpty()) {
            for (ItemPedidoRequestDTO itemDto : dto.getItens()) {
                ItemPedido itemPedido = new ItemPedido(); // Corrected from PedidoItem
                itemPedido.setQuantidade(itemDto.getQuantidade());
                itemPedido.setPrecoUnitario(itemDto.getPrecoUnitario());
                itemPedido.setPedido(pedido);
                pedido.getItens().add(itemPedido);
                totalCalculado += itemDto.getQuantidade() * itemDto.getPrecoUnitario();
            }
        }
        pedido.setTotalPedido(totalCalculado);

        pedidoRepository.persist(pedido);
        return PedidoResponseDTO.valueOf(pedido);
    }

    @Transactional
    public PedidoResponseDTO update(Long id, PedidoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new ServiceException("Pedido não encontrado.", Response.Status.NOT_FOUND);
        }

        String userEmail = securityContext.getUserPrincipal().getName();
        boolean isAdmin = securityContext.isUserInRole("ADMIN");

        if (!isAdmin && !pedido.getCliente().getEmail().equals(userEmail)) {
            throw new ServiceException("Usuário não autorizado a atualizar este pedido.", Response.Status.FORBIDDEN);
        }

        // Update delivery address if provided and authorized
        if (dto.getIdEnderecoEntrega() != null) {
            Endereco enderecoEntrega = enderecoRepository.findById(dto.getIdEnderecoEntrega());
            if (enderecoEntrega == null) {
                throw new ServiceException("Endereço de entrega não encontrado.", Response.Status.NOT_FOUND);
            }
            if (!isAdmin && !enderecoEntrega.getCliente().getId().equals(pedido.getCliente().getId())) {
                throw new ServiceException("Endereço de entrega não pertence ao cliente do pedido.", Response.Status.FORBIDDEN);
            }
            pedido.setEnderecoEntrega(enderecoEntrega);
        }


        if (dto.getItens() != null) {
             pedido.getItens().clear();
             Double totalCalculado = 0.0;
             for (ItemPedidoRequestDTO itemDto : dto.getItens()) {
                 ItemPedido itemPedido = new ItemPedido(); // Corrected from PedidoItem
                 itemPedido.setQuantidade(itemDto.getQuantidade());
                 itemPedido.setPrecoUnitario(itemDto.getPrecoUnitario());
                 itemPedido.setPedido(pedido);
                 pedido.getItens().add(itemPedido);
                 totalCalculado += itemDto.getQuantidade() * itemDto.getPrecoUnitario();
             }
             pedido.setTotalPedido(totalCalculado);
        }

        pedidoRepository.persist(pedido);
        return PedidoResponseDTO.valueOf(pedido);
    }

    @Transactional
    public void delete(Long id) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new ServiceException("Pedido não encontrado.", Response.Status.NOT_FOUND);
        }

        String userEmail = securityContext.getUserPrincipal().getName();
        boolean isAdmin = securityContext.isUserInRole("ADMIN");

        if (!isAdmin && !pedido.getCliente().getEmail().equals(userEmail)) {
            throw new ServiceException("Usuário não autorizado a deletar este pedido.", Response.Status.FORBIDDEN);
        }

        pedidoRepository.delete(pedido);
    }

    public List<PedidoResponseDTO> findAll() {
        return pedidoRepository.listAll().stream()
                .map(PedidoResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    public PedidoResponseDTO findById(Long id) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new ServiceException("Pedido não encontrado.", Response.Status.NOT_FOUND);
        }

        String userEmail = securityContext.getUserPrincipal().getName();
        boolean isAdmin = securityContext.isUserInRole("ADMIN");

        if (!isAdmin && !pedido.getCliente().getEmail().equals(userEmail)) {
            throw new ServiceException("Usuário não autorizado a visualizar este pedido.", Response.Status.FORBIDDEN);
        }

        return PedidoResponseDTO.valueOf(pedido);
    }

    public List<PedidoResponseDTO> getPurchaseHistoryForAuthenticatedUser() {
        String userEmail = securityContext.getUserPrincipal().getName();
        Cliente cliente = clienteRepository.findByEmail(userEmail);

        if (cliente == null) {
            throw new ServiceException("Cliente autenticado não encontrado.", Response.Status.UNAUTHORIZED);
        }
        return pedidoRepository.findByCliente(cliente).stream()
                .map(PedidoResponseDTO::valueOf)
                .collect(Collectors.toList());
    }
}