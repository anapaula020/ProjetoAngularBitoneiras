package br.unitins.tp1.dto;

import br.unitins.tp1.model.Perfil;
import br.unitins.tp1.model.Usuario;

public record UsuarioResponseDTO(Long id, String username, String email, String senha, String cpf,
        EnderecoResponseDTO endereco, Perfil perfil) {
    public static UsuarioResponseDTO valueOf(Usuario user) {
        if (user == null) {
            return null;
        }
        EnderecoResponseDTO endereco = (user.getEndereco() != null ? EnderecoResponseDTO.valueOf(user.getEndereco()) : null);
        return new UsuarioResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getSenha(), user.getCpf(),
                endereco, user.getPerfil());
    }
}