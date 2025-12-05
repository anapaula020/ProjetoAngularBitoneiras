package mssaat.org.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.constraints.Email;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Administrador extends DefaultEntity {
    @Column(length = 80, nullable = false, unique = true)
    private String username;
    @Column(length = 60, nullable = false)
    @Email
    private String email;
    @Column(length = 120, nullable = false)
    private String senha;
    @Column(length = 12, nullable = false)
    private String cpf;

    public Administrador() {
    }

    public Administrador(String username, @Email String email, String senha, String cpf) {
        this.username = username;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
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
}