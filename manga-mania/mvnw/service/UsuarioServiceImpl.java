package mssaat.org.service;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;
import mssaat.org.DTO.EmailDTO;
import mssaat.org.DTO.EnderecoDTO;

import mssaat.org.DTO.TrocaSenhaDTO;
import mssaat.org.DTO.UsuarioDTO;
import mssaat.org.DTO.UsuarioResponseDTO;
import mssaat.org.model.Endereco;
import mssaat.org.model.Sexo;

import mssaat.org.model.Usuario;
import mssaat.org.repository.UsuarioRepository;
import mssaat.org.validation.ValidationException;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {
    @Inject
    public UsuarioRepository usuarioRepository;
    @Inject
    public HashService hashService;
    @Inject
    public JsonWebToken jsonWebToken;

    @Override
    @Transactional
    public UsuarioResponseDTO create(@Valid UsuarioDTO userDto) {
        Usuario userBanco = new Usuario();
        userBanco.setUsername(userDto.username());
        userBanco.setEmail(userDto.email());
        userBanco.setSenha(hashService.getHashSenha(userDto.senha()));
        userBanco.setCpf(userDto.cpf());
    
        userBanco.setSexo(Sexo.valueOf(userDto.sexo()));
        if(userDto.endereco()!=null){
            Endereco endereco = new Endereco();
            endereco.setRua(userDto.endereco().rua());
            endereco.setNumero(userDto.endereco().numero());
            endereco.setCidade(userDto.endereco().cidade());
            endereco.setEstado(userDto.endereco().estado());
            endereco.setCep(userDto.endereco().cep());
            userBanco.setEndereco(endereco);
        }
        usuarioRepository.persist(userBanco);
        return UsuarioResponseDTO.valueOf(userBanco);
    }
    
    @Override
    @Transactional
    public Response updateSenha(TrocaSenhaDTO senhaDTO) {
        Usuario usuario = usuarioRepository.findNomeEqual(jsonWebToken.getName());
        String novaSenha = senhaDTO.novaSenha();
        String confirmacao = senhaDTO.confirmacao();
        String senhaAtual = senhaDTO.senhaAtual();
        if(!(novaSenha.equals(confirmacao))){
            throw new ValidationException("confirmacao","Senhas divergentes");
        }
        if(hashService.getHashSenha(senhaAtual).equals(usuario.getSenha())){
            usuario.setSenha(hashService.getHashSenha(novaSenha));
            UsuarioResponseDTO user = UsuarioResponseDTO.valueOf(usuario);
            return Response.ok(user).build();
        }else{
            throw new ValidationException("senhaAtual","Senha atual incorreta");
        }
        
    }

    @Override
    @Transactional
    public Response updateEmail(EmailDTO email) {
        Usuario userBanco = usuarioRepository.findNomeEqual(jsonWebToken.getName());
        if (userBanco == null) {
            throw new ValidationException("id", "Usuário não existe.");
        }
        userBanco.setEmail(email.email());
        return Response.ok(userBanco).build();
    }

    @Override
    @Transactional
    public Response updateEndereco(EnderecoDTO enderecoDTO) {
    Usuario userBanco = usuarioRepository.findNomeEqual(jsonWebToken.getName());
    if (userBanco == null) {
        throw new ValidationException("id", "Usuário não existe.");
    }
        Endereco endereco = new Endereco();
        endereco.setRua(enderecoDTO.rua());
        endereco.setNumero(enderecoDTO.numero());
        endereco.setCidade(enderecoDTO.cidade());
        endereco.setEstado(enderecoDTO.estado());
        endereco.setCep(enderecoDTO.cep());
        userBanco.setEndereco(endereco);
        UsuarioResponseDTO user = UsuarioResponseDTO.valueOf(userBanco);
        return Response.ok(user).build();
    }
    @Override
    @Transactional
    public void update(Long id, UsuarioDTO userDto) {
        Usuario userBanco = usuarioRepository.findById(id);
        if (userBanco == null) {
            throw new ValidationException("id", "Usuário não existe.");
        }
        userBanco.setUsername(userDto.username());
        userBanco.setEmail(userDto.email());
        userBanco.setSenha(hashService.getHashSenha(userDto.senha()));
        userBanco.setCpf(userDto.cpf());
        Endereco endereco = new Endereco();
        endereco.setRua(userDto.endereco().rua());
        endereco.setNumero(userDto.endereco().numero());
        endereco.setCidade(userDto.endereco().cidade());
        endereco.setEstado(userDto.endereco().estado());
        endereco.setCep(userDto.endereco().cep());
        userBanco.setEndereco(endereco);
       
        userBanco.setSexo(Sexo.valueOf(userDto.sexo()));

        
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public long count() {
        return usuarioRepository.count();
    }

    @Override
    public List<UsuarioResponseDTO> findAll(int page, int pageSize) {
        List<Usuario> usuarios = usuarioRepository.findAll().page(page, pageSize).list();
        return usuarios.stream().map(UsuarioResponseDTO::valueOf).collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO findById(Long id) {
        Usuario user = usuarioRepository.findById(id);
        if (user == null) {
            return null;
        }
        return UsuarioResponseDTO.valueOf(user);
    }

    @Override
    public List<UsuarioResponseDTO> findByUsername(String content, int page, int pageSize) {
        List<Usuario> usuarios = usuarioRepository.findByUsername(content).page(page, pageSize).list();
        return usuarios.stream().map(UsuarioResponseDTO::valueOf).collect(Collectors.toList());
    }

    @Override
    public List<UsuarioResponseDTO> findByEmail(String email, int page, int pageSize) {
        List<Usuario> usuarios = usuarioRepository.findByEmail(email).page(page, pageSize).list();
        return usuarios.stream().map(UsuarioResponseDTO::valueOf).collect(Collectors.toList());
    }

    @Override
    public List<UsuarioResponseDTO> findByCpf(String cpf, int page, int pageSize) {
        List<Usuario> usuarios = usuarioRepository.findByCpf(cpf).page(page, pageSize).list();
        return usuarios.stream().map(UsuarioResponseDTO::valueOf).collect(Collectors.toList());
    }

    @Override
    public List<UsuarioResponseDTO> findByEndereco(String content, int page, int pageSize) {
        List<Usuario> usuarios = usuarioRepository.findByEndereco(content).page(page, pageSize).list();
        return usuarios.stream().map(UsuarioResponseDTO::valueOf).collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO login(String username, String senha) {
        Usuario usuario = usuarioRepository.findByUsernameAndSenha(username, senha);
        return UsuarioResponseDTO.valueOf(usuario);
    }
}