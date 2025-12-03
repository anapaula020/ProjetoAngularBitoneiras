package br.unitins.tp1.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public class ClienteRequestDTO { // Mudado de record para class

    @NotBlank(message = "O nome não pode estar em branco.")
    private String nome;

    @Email(message = "Email inválido.")
    @NotBlank(message = "O email não pode estar em branco.")
    private String email;

    @NotBlank(message = "A senha não pode estar em branco.")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
    private String senha;

    @NotBlank(message = "O CPF não pode estar em branco.")
    @Pattern(regexp = "^\\d{11}$", message = "CPF inválido. Deve conter 11 dígitos.")
    private String cpf;

    @Past(message = "A data de nascimento deve ser no passado.")
    @NotNull(message = "A data de nascimento não pode ser nula.")
    private LocalDate dataNascimento;

    @NotBlank(message = "O telefone não pode estar em branco.")
    private String telefone;

    @NotBlank(message = "O sexo não pode estar em branco.")
    private String sexo;

    private List<EnderecoRequestDTO> enderecos; // New field for delivery addresses

    // Construtor padrão
    public ClienteRequestDTO() {
    }

    // Construtor completo (ajuste conforme necessário)
    public ClienteRequestDTO(String nome, String email, String senha, String cpf, LocalDate dataNascimento, String telefone, String sexo, List<EnderecoRequestDTO> enderecos) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.sexo = sexo;
        this.enderecos = enderecos;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public List<EnderecoRequestDTO> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<EnderecoRequestDTO> enderecos) {
        this.enderecos = enderecos;
    }
}