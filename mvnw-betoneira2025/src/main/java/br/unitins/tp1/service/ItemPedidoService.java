package br.unitins.tp1.service;

import br.unitins.tp1.dto.ItemPedidoRequestDTO;
import br.unitins.tp1.dto.ItemPedidoResponseDTO;
import br.unitins.tp1.exception.ServiceException;
import br.unitins.tp1.model.ItemPedido;
import br.unitins.tp1.repository.ItemPedidoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ItemPedidoService {

    @Inject
    ItemPedidoRepository itemPedidoRepository;

    @Inject
    SecurityContext securityContext;

    @Transactional
    public ItemPedidoResponseDTO create(ItemPedidoRequestDTO dto) {
        if (!securityContext.isUserInRole("ADMIN")) {
            throw new ServiceException("Usuário não autorizado a criar itens de pedido diretamente.", Response.Status.FORBIDDEN);
        }
        ItemPedido itemPedido = new ItemPedido();
        // itemPedido.setBetoneira(betoneiraRepository.findById(dto.getIdBetoneira())); // Usar getIdBetoneira()
        // itemPedido.setPedido(pedidoRepository.findById(dto.getPedidoId())); // Assumindo que ItemPedidoRequestDTO tem getPedidoId()
        itemPedido.setQuantidade(dto.getQuantidade()); // Usar getQuantidade()
        itemPedido.setPrecoUnitario(dto.getPrecoUnitario()); // Usar getPrecoUnitario()

        itemPedidoRepository.persist(itemPedido);
        return ItemPedidoResponseDTO.valueOf(itemPedido);
    }

    @Transactional
    public ItemPedidoResponseDTO update(Long id, ItemPedidoRequestDTO dto) {
        if (!securityContext.isUserInRole("ADMIN")) {
            throw new ServiceException("Usuário não autorizado a atualizar itens de pedido.", Response.Status.FORBIDDEN);
        }
        ItemPedido itemPedido = itemPedidoRepository.findById(id);
        if (itemPedido == null) {
            throw new ServiceException("Item de pedido não encontrado.", Response.Status.NOT_FOUND);
        }

        // itemPedido.setBetoneira(betoneiraRepository.findById(dto.getIdBetoneira()));
        // itemPedido.setPedido(pedidoRepository.findById(dto.getPedidoId()));
        itemPedido.setQuantidade(dto.getQuantidade()); // Usar getQuantidade()
        itemPedido.setPrecoUnitario(dto.getPrecoUnitario()); // Usar getPrecoUnitario()

        itemPedidoRepository.persist(itemPedido);
        return ItemPedidoResponseDTO.valueOf(itemPedido);
    }

    @Transactional
    public void delete(Long id) {
        if (!securityContext.isUserInRole("ADMIN")) {
            throw new ServiceException("Usuário não autorizado a deletar itens de pedido.", Response.Status.FORBIDDEN);
        }
        ItemPedido itemPedido = itemPedidoRepository.findById(id);
        if (itemPedido == null) {
            throw new ServiceException("Item de pedido não encontrado.", Response.Status.NOT_FOUND);
        }
        itemPedidoRepository.delete(itemPedido);
    }

    public List<ItemPedidoResponseDTO> findAll() {
        return itemPedidoRepository.listAll().stream()
                .map(ItemPedidoResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    public ItemPedidoResponseDTO findById(Long id) {
        ItemPedido itemPedido = itemPedidoRepository.findById(id);
        if (itemPedido == null) {
            throw new ServiceException("Item de pedido não encontrado.", Response.Status.NOT_FOUND);
        }

        String userEmail = securityContext.getUserPrincipal().getName();
        boolean isAdmin = securityContext.isUserInRole("ADMIN");

        if (!isAdmin && (itemPedido.getPedido() == null || itemPedido.getPedido().getCliente() == null || !itemPedido.getPedido().getCliente().getEmail().equals(userEmail))) {
            throw new ServiceException("Usuário não autorizado a visualizar este item de pedido.", Response.Status.FORBIDDEN);
        }

        return ItemPedidoResponseDTO.valueOf(itemPedido);
    }
}