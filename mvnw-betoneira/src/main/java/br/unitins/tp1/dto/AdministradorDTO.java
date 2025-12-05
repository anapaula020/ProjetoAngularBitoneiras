package br.unitins.tp1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdministradorDTO(
        @NotBlank(message = "Nome de usuário não pode ficar vazio.") @Size(min = 4, message = "Nome de usuário está pequeno demais.") @Size(max = 80, message = "Nome de usuário está grande demais.") String username,
        @NotBlank(message = "Senha não pode ficar vazia.") @Size(min = 6, message = "Senha está pequena demais.") @Size(max = 60, message = "Senha está grande demais.") String senha) {
}