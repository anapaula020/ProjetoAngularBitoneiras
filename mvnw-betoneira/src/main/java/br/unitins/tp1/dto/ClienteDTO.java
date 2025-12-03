// src/main/java/br/unitins/tp1/dto/ClienteDTO.java
package br.unitins.tp1.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ClienteDTO {

    @NotBlank(message = "O nome não pode ser nulo ou vazio.")
    private String nome;

    @NotBlank(message = "O email não pode ser nulo ou vazio.")
    @Email(message = "Formato de email inválido.")
    private String email;

    @NotBlank(message = "O CPF não pode ser nulo ou vazio.")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos.")
    private String cpf;

    // A senha pode ser opcional em um DTO de atualização parcial
    private String senha;

    // Construtor padrão
    public ClienteDTO() {
    }

    // Construtor completo
    public ClienteDTO(String nome, String email, String cpf, String senha) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.senha = senha;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public String getSenha() {
        return senha;
    }

    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}