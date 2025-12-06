package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.EnderecoDTO;
import br.unitins.tp1.dto.TrocaSenhaDTO;
import br.unitins.tp1.dto.UsuarioDTO;
import br.unitins.tp1.dto.UsuarioResponseDTO;
import br.unitins.tp1.dto.mercadopago.EmailDTO;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;

public interface UsuarioService {
    public UsuarioResponseDTO create(@Valid UsuarioDTO usuarioDto);

    public UsuarioResponseDTO login(String username, String senha);

    public UsuarioResponseDTO findById(Long id);

    public long count();

    public Response updateSenha(@Valid TrocaSenhaDTO senha);

    public Response updateEndereco(@Valid EnderecoDTO endereco);

    public Response updateEmail(@Valid EmailDTO email);

    public List<UsuarioResponseDTO> findAll(int page, int pageSize);

    public List<UsuarioResponseDTO> findByUsername(String username, int page, int pageSize);

    public List<UsuarioResponseDTO> findByEmail(String email, int page, int pageSize);

    public List<UsuarioResponseDTO> findByCpf(String cpf, int page, int pageSize);

    public List<UsuarioResponseDTO> findByEndereco(String endereco, int page, int pageSize);

    public void update(Long id, @Valid UsuarioDTO dto);

    public void deleteById(Long id);

    public UsuarioResponseDTO findByLoginAndSenha(String username, String senha);
}