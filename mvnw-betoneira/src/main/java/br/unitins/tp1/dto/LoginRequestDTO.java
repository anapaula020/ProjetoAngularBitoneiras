// src/main/java/br/unitins/tp1/dto/LoginRequestDTO.java
package br.unitins.tp1.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequestDTO {
    @Email(message = "Email inválido.")
    @NotBlank(message = "O email é obrigatório.")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    private String senha;

    // Construtor padrão
    public LoginRequestDTO() {}

    // Construtor com todos os campos (útil para testes)
    public LoginRequestDTO(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}