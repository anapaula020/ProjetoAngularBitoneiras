package br.unitins.tp1.dto;

import br.unitins.tp1.model.Cliente;
import br.unitins.tp1.model.Perfil;

public record ClienteResponseDTO(Long id, String username, String email, String senha, String cpf,
        EnderecoResponseDTO endereco, Perfil perfil) {
    public static ClienteResponseDTO valueOf(Cliente user) {
        if (user == null) {
            return null;
        }
        EnderecoResponseDTO endereco = (user.getEndereco() != null ? EnderecoResponseDTO.valueOf(user.getEndereco()) : null);
        return new ClienteResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getSenha(), user.getCpf(),
                endereco, user.getPerfil());
    }
}