package br.unitins.tp1.service;

import br.unitins.tp1.dto.ClienteRequestDTO;
import br.unitins.tp1.dto.ClienteResponseDTO;
import br.unitins.tp1.exception.ServiceException;
import br.unitins.tp1.model.Cliente;
import br.unitins.tp1.repository.ClienteRepository;
import br.unitins.tp1.utils.HashUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {
    @Inject
    ClienteRepository clienteRepository;

    @Transactional
    public ClienteResponseDTO create(ClienteRequestDTO dto) {
        if (clienteRepository.findByEmail(dto.getEmail()) != null) {
            throw new ServiceException("Email já cadastrado.", Response.Status.CONFLICT);
        }

        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setSenha(HashUtil.hash(dto.getSenha()));
        cliente.setCpf(dto.getCpf());
        cliente.setTelefone(dto.getTelefone());
        cliente.setRole("USER");

        clienteRepository.persist(cliente);
        return ClienteResponseDTO.valueOf(cliente);
    }

    @Transactional
    public ClienteResponseDTO update(Long id, ClienteRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            throw new ServiceException("Cliente não encontrado.", Response.Status.NOT_FOUND);
        }

        if (!cliente.getEmail().equals(dto.getEmail()) && clienteRepository.findByEmail(dto.getEmail()) != null) {
            throw new ServiceException("Email já cadastrado para outro cliente.", Response.Status.CONFLICT);
        }

        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setSenha(HashUtil.hash(dto.getSenha()));
        cliente.setCpf(dto.getCpf());
        cliente.setTelefone(dto.getTelefone());

        clienteRepository.persist(cliente);
        return ClienteResponseDTO.valueOf(cliente);
    }

    @Transactional
    public void delete(Long id) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            throw new ServiceException("Cliente não encontrado.", Response.Status.NOT_FOUND);
        }
        clienteRepository.delete(cliente);
    }

    public List<ClienteResponseDTO> findAll() {
        return clienteRepository.listAll().stream()
                .map(ClienteResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    public ClienteResponseDTO findById(Long id) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            throw new ServiceException("Cliente não encontrado.", Response.Status.NOT_FOUND);
        }
        return ClienteResponseDTO.valueOf(cliente);
    }

    public List<ClienteResponseDTO> findByNome(String nome) {
        return clienteRepository.findByNome(nome).stream()
                .map(ClienteResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    public ClienteResponseDTO findByEmailAndSenha(String email, String rawSenha) {
        Cliente cliente = clienteRepository.findByEmail(email);
        if (cliente == null || !HashUtil.verifyPassword(rawSenha, cliente.getSenha())) {
            throw new ServiceException("Email ou senha inválidos.", Response.Status.UNAUTHORIZED);
        }
        return ClienteResponseDTO.valueOf(cliente);
    }
}