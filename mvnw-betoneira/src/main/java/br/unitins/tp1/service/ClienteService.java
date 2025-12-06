package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.EnderecoDTO;
import br.unitins.tp1.dto.TrocaSenhaDTO;
import br.unitins.tp1.dto.ClienteDTO;
import br.unitins.tp1.dto.ClienteResponseDTO;
import br.unitins.tp1.dto.mercadopago.EmailDTO;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;

public interface ClienteService {
    public ClienteResponseDTO create(@Valid ClienteDTO clienteDTO);

    public ClienteResponseDTO login(String username, String senha);

    public ClienteResponseDTO findById(Long id);

    public long count();

    public Response updateSenha(@Valid TrocaSenhaDTO senha);

    public Response updateEndereco(@Valid EnderecoDTO endereco);

    public Response updateEmail(@Valid EmailDTO email);

    public List<ClienteResponseDTO> findAll(int page, int pageSize);

    public List<ClienteResponseDTO> findByUsername(String username, int page, int pageSize);

    public List<ClienteResponseDTO> findByEmail(String email, int page, int pageSize);

    public List<ClienteResponseDTO> findByCpf(String cpf, int page, int pageSize);

    public List<ClienteResponseDTO> findByEndereco(String endereco, int page, int pageSize);

    public void update(Long id, @Valid ClienteDTO dto);

    public void deleteById(Long id);

    public ClienteResponseDTO findByLoginAndSenha(String username, String senha);
}