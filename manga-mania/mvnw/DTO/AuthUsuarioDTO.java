package mssaat.org.DTO;

import jakarta.validation.constraints.NotBlank;

public record AuthUsuarioDTO(
        @NotBlank(message = "Nome não pode ficar vazio.") String username,
        @NotBlank(message = "Senha não pode ficar vazia.") String senha) {
}