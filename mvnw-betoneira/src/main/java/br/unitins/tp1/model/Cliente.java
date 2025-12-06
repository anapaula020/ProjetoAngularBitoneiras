package br.unitins.tp1.model;

import java.util.Set;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;

@Entity
public class Cliente extends DefaultEntity {
    @Column(length = 80, unique = true, nullable = false)
    private String username;
    @Column(length = 60)
    @Email
    private String email;
    @Column(length = 120)
    private String senha;
    @Column(length = 12)
    private String cpf;
    @Column(nullable = true)
    private Endereco endereco;
    private Perfil perfil;

    public Cliente() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Set<String> getRolesAsSet() {
        return Set.of(perfil.name());
    }
}