// src/main/java/br/unitins/tp1/model/Cliente.java
package br.unitins.tp1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.HashSet; // Added for Set implementation

@Entity
public class Cliente extends DefaultEntity {
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String senha;
    private String cpf;
    private String telefone;

    // Assuming a simple role management, e.g., "ADMIN" or "USER" stored as a string
    // For more complex roles, a separate entity or collection table would be used.
    private String role; // Added for role management

    @OneToMany(mappedBy = "cliente")
    private List<Endereco> enderecos;

    // Constructors
    public Cliente() {
    }

    public Cliente(String nome, String email, String senha, String cpf, String telefone, String role) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.telefone = telefone;
        this.role = role; // Initializing role
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    // New method for roles as a Set, required by ClienteResponseDTO and TokenService
    public Set<String> getRolesAsSet() {
        if (this.role == null || this.role.isEmpty()) {
            return Collections.emptySet();
        }
        // Assuming role is a single string like "ADMIN" or "USER"
        Set<String> roles = new HashSet<>();
        roles.add(this.role);
        return roles;
    }

    // Getters and Setters for role
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }
}