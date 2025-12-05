package br.unitins.tp1.dto;

import br.unitins.tp1.model.Administrador;

public record AdministradorResponseDTO(Long id, String username, String email, String senha, String cpf) {
    public static AdministradorResponseDTO valueOf(Administrador admin) {
        if(admin == null) return null;
        return new AdministradorResponseDTO(admin.getId(), admin.getUsername(), admin.getEmail(), admin.getSenha(), admin.getCpf());
    }
}