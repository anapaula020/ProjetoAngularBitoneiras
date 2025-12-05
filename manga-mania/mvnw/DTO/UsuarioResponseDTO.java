package mssaat.org.DTO;


import mssaat.org.model.Sexo;
import mssaat.org.model.Usuario;

public record UsuarioResponseDTO(Long id, String username, String email, String senha, String cpf, EnderecoResponseDTO endereco,  Sexo sexo) {
    public static UsuarioResponseDTO valueOf(Usuario user) {
        if(user == null) return null;
        EnderecoResponseDTO endereco = (user.getEndereco() != null ? EnderecoResponseDTO.valueOf(user.getEndereco()) : null);
        return new UsuarioResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getSenha(), user.getCpf(), endereco, user.getSexo());
    }
}