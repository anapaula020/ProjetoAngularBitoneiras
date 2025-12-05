package br.unitins.tp1.service;

import br.unitins.tp1.dto.EnderecoRequestDTO;
import br.unitins.tp1.dto.EnderecoResponseDTO;
import br.unitins.tp1.exception.ServiceException;
import br.unitins.tp1.model.Cliente;
import br.unitins.tp1.model.Endereco;
import br.unitins.tp1.repository.ClienteRepository;
import br.unitins.tp1.repository.EnderecoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class EnderecoService {

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    SecurityContext securityContext;

    @Transactional
    public EnderecoResponseDTO create(EnderecoRequestDTO dto) {
        String userEmail = securityContext.getUserPrincipal().getName();
        Cliente cliente = clienteRepository.findByEmail(userEmail);
        if (cliente == null) {
            throw new ServiceException("Cliente autenticado não encontrado.", Response.Status.UNAUTHORIZED);
        }

        Endereco endereco = new Endereco();
        endereco.setLogradouro(dto.getLogradouro());
        endereco.setNumero(dto.getNumero());
        endereco.setComplemento(dto.getComplemento());
        endereco.setBairro(dto.getBairro());
        endereco.setCidade(dto.getCidade());
        endereco.setEstado(dto.getEstado());
        endereco.setCep(dto.getCep());
        endereco.setCliente(cliente);

        enderecoRepository.persist(endereco);
        return EnderecoResponseDTO.valueOf(endereco);
    }

    @Transactional
    public EnderecoResponseDTO update(Long id, EnderecoRequestDTO dto) {
        Endereco endereco = enderecoRepository.findById(id);
        if (endereco == null) {
            throw new ServiceException("Endereço não encontrado.", Response.Status.NOT_FOUND);
        }

        String userEmail = securityContext.getUserPrincipal().getName();
        boolean isAdmin = securityContext.isUserInRole("ADMIN");

        if (!isAdmin && !endereco.getCliente().getEmail().equals(userEmail)) {
            throw new ServiceException("Usuário não autorizado a atualizar este endereço.", Response.Status.FORBIDDEN);
        }

        endereco.setLogradouro(dto.getLogradouro());
        endereco.setNumero(dto.getNumero());
        endereco.setComplemento(dto.getComplemento());
        endereco.setBairro(dto.getBairro());
        endereco.setCidade(dto.getCidade());
        endereco.setEstado(dto.getEstado());
        endereco.setCep(dto.getCep());

        enderecoRepository.persist(endereco);
        return EnderecoResponseDTO.valueOf(endereco);
    }

    @Transactional
    public void delete(Long id) {
        Endereco endereco = enderecoRepository.findById(id);
        if (endereco == null) {
            throw new ServiceException("Endereço não encontrado.", Response.Status.NOT_FOUND);
        }

        String userEmail = securityContext.getUserPrincipal().getName();
        boolean isAdmin = securityContext.isUserInRole("ADMIN");

        if (!isAdmin && !endereco.getCliente().getEmail().equals(userEmail)) {
            throw new ServiceException("Usuário não autorizado a deletar este endereço.", Response.Status.FORBIDDEN);
        }

        enderecoRepository.delete(endereco);
    }

    public List<EnderecoResponseDTO> findAll() {
        return enderecoRepository.listAll().stream()
                .map(EnderecoResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    public EnderecoResponseDTO findById(Long id) {
        Endereco endereco = enderecoRepository.findById(id);
        if (endereco == null) {
            throw new ServiceException("Endereço não encontrado.", Response.Status.NOT_FOUND);
        }

        String userEmail = securityContext.getUserPrincipal().getName();
        boolean isAdmin = securityContext.isUserInRole("ADMIN");

        if (!isAdmin && !endereco.getCliente().getEmail().equals(userEmail)) {
            throw new ServiceException("Usuário não autorizado a visualizar este endereço.", Response.Status.FORBIDDEN);
        }

        return EnderecoResponseDTO.valueOf(endereco);
    }

    public List<EnderecoResponseDTO> findByAuthenticatedUser() {
        String userEmail = securityContext.getUserPrincipal().getName();
        Cliente cliente = clienteRepository.findByEmail(userEmail);
        if (cliente == null) {
            throw new ServiceException("Cliente autenticado não encontrado.", Response.Status.UNAUTHORIZED);
        }
        return enderecoRepository.findByCliente(cliente).stream()
                .map(EnderecoResponseDTO::valueOf)
                .collect(Collectors.toList());
    }
}