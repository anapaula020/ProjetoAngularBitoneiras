package mssaat.org.DTO;

import mssaat.org.model.Administrador;

public record AdministradorResponseDTO(Long id, String username, String email, String senha, String cpf) {
    public static AdministradorResponseDTO valueOf(Administrador admin) {
        if(admin == null) return null;
        return new AdministradorResponseDTO(admin.getId(), admin.getUsername(), admin.getEmail(), admin.getSenha(), admin.getCpf());
    }
}