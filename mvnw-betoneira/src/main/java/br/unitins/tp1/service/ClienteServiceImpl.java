package br.unitins.tp1.service;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.tp1.dto.EnderecoDTO;
import br.unitins.tp1.dto.TrocaSenhaDTO;
import br.unitins.tp1.dto.ClienteDTO;
import br.unitins.tp1.dto.ClienteResponseDTO;
import br.unitins.tp1.dto.mercadopago.EmailDTO;
import br.unitins.tp1.model.Endereco;
import br.unitins.tp1.model.Cliente;
import br.unitins.tp1.repository.ClienteRepository;
import br.unitins.tp1.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {
    @Inject
    public ClienteRepository clienteRepository;
    @Inject
    public HashService hashService;
    @Inject
    public JsonWebToken jsonWebToken;

    @Override
    @Transactional
    public ClienteResponseDTO create(@Valid ClienteDTO userDto) {
        Cliente userBanco = new Cliente();
        userBanco.setUsername(userDto.username());
        userBanco.setEmail(userDto.email());
        userBanco.setSenha(hashService.getHashSenha(userDto.senha()));
        userBanco.setCpf(userDto.cpf());

        if (userDto.endereco() != null) {
            Endereco endereco = new Endereco();
            endereco.setNumero(endereco.getNumero());
            endereco.setComplemento(endereco.getComplemento());
            endereco.setBairro(endereco.getBairro());
            endereco.setCidade(endereco.getCidade());
            endereco.setEstado(endereco.getEstado());
            endereco.setCep(endereco.getCep());
            userBanco.setEndereco(endereco);
        }
        clienteRepository.persist(userBanco);
        return ClienteResponseDTO.valueOf(userBanco);
    }

    @Override
    @Transactional
    public Response updateSenha(TrocaSenhaDTO senhaDTO) {
        Cliente cliente = clienteRepository.findNomeEqual(jsonWebToken.getName());
        String novaSenha = senhaDTO.novaSenha();
        String confirmacao = senhaDTO.confirmacao();
        String senhaAtual = senhaDTO.senhaAtual();
        if (!(novaSenha.equals(confirmacao))) {
            throw new ValidationException("confirmacao", "Senhas divergentes");
        }
        if (hashService.getHashSenha(senhaAtual).equals(cliente.getSenha())) {
            cliente.setSenha(hashService.getHashSenha(novaSenha));
            ClienteResponseDTO user = ClienteResponseDTO.valueOf(cliente);
            return Response.ok(user).build();
        } else {
            throw new ValidationException("senhaAtual", "Senha atual incorreta");
        }

    }

    @Override
    @Transactional
    public Response updateEmail(EmailDTO email) {
        Cliente userBanco = clienteRepository.findNomeEqual(jsonWebToken.getName());
        if (userBanco == null) {
            throw new ValidationException("id", "Usuário não existe.");
        }
        userBanco.setEmail(email.email());
        return Response.ok(userBanco).build();
    }

    @Override
    @Transactional
    public Response updateEndereco(EnderecoDTO enderecoDTO) {
        Cliente userBanco = clienteRepository.findNomeEqual(jsonWebToken.getName());
        if (userBanco == null) {
            throw new ValidationException("id", "Usuário não existe.");
        }
        Endereco endereco = new Endereco();
        endereco.setNumero(endereco.getNumero());
        endereco.setComplemento(endereco.getComplemento());
        endereco.setBairro(endereco.getBairro());
        endereco.setCidade(endereco.getCidade());
        endereco.setEstado(endereco.getEstado());
        endereco.setCep(endereco.getCep());
        userBanco.setEndereco(endereco);
        ClienteResponseDTO user = ClienteResponseDTO.valueOf(userBanco);
        return Response.ok(user).build();
    }

    @Override
    @Transactional
    public void update(Long id, ClienteDTO userDto) {
        Cliente userBanco = clienteRepository.findById(id);
        if (userBanco == null) {
            throw new ValidationException("id", "Usuário não existe.");
        }
        userBanco.setUsername(userDto.username());
        userBanco.setEmail(userDto.email());
        userBanco.setSenha(hashService.getHashSenha(userDto.senha()));
        userBanco.setCpf(userDto.cpf());

        Endereco endereco = new Endereco();
        endereco.setNumero(endereco.getNumero());
        endereco.setComplemento(endereco.getComplemento());
        endereco.setBairro(endereco.getBairro());
        endereco.setCidade(endereco.getCidade());
        endereco.setEstado(endereco.getEstado());
        endereco.setCep(endereco.getCep());
        userBanco.setEndereco(endereco);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public long count() {
        return clienteRepository.count();
    }

    @Override
    public List<ClienteResponseDTO> findAll(int page, int pageSize) {
        List<Cliente> clientes = clienteRepository.findAll().page(page, pageSize).list();
        return clientes.stream().map(ClienteResponseDTO::valueOf).collect(Collectors.toList());
    }

    @Override
    public ClienteResponseDTO findById(Long id) {
        Cliente user = clienteRepository.findById(id);
        if (user == null) {
            return null;
        }
        return ClienteResponseDTO.valueOf(user);
    }

    @Override
    public List<ClienteResponseDTO> findByUsername(String content, int page, int pageSize) {
        List<Cliente> clientes = clienteRepository.findByUsername(content).page(page, pageSize).list();
        return clientes.stream().map(ClienteResponseDTO::valueOf).collect(Collectors.toList());
    }

    @Override
    public List<ClienteResponseDTO> findByEmail(String email, int page, int pageSize) {
        List<Cliente> clientes = clienteRepository.findByEmail(email).page(page, pageSize).list();
        return clientes.stream().map(ClienteResponseDTO::valueOf).collect(Collectors.toList());
    }

    @Override
    public List<ClienteResponseDTO> findByCpf(String cpf, int page, int pageSize) {
        List<Cliente> clientes = clienteRepository.findByCpf(cpf).page(page, pageSize).list();
        return clientes.stream().map(ClienteResponseDTO::valueOf).collect(Collectors.toList());
    }

    @Override
    public List<ClienteResponseDTO> findByEndereco(String content, int page, int pageSize) {
        List<Cliente> clientes = clienteRepository.findByEndereco(content).page(page, pageSize).list();
        return clientes.stream().map(ClienteResponseDTO::valueOf).collect(Collectors.toList());
    }

    @Override
    public ClienteResponseDTO login(String username, String senha) {
        Cliente cliente = clienteRepository.findByUsernameAndSenha(username, senha);
        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    public ClienteResponseDTO findByLoginAndSenha(String username, String senha) {
        Cliente cliente = clienteRepository.findByUsernameAndSenha(username, senha);
        return ClienteResponseDTO.valueOf(cliente);
    }
}