package br.unitins.tp1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern; // Importação adicionada
import jakarta.validation.constraints.Size;

public class EnderecoRequestDTO {
    @NotBlank(message = "O logradouro não pode estar em branco.")
    private String logradouro;

    @NotBlank(message = "O número não pode estar em branco.")
    private String numero;

    private String complemento;

    @NotBlank(message = "O bairro não pode estar em branco.")
    private String bairro;

    @NotBlank(message = "A cidade não pode estar em branco.")
    private String cidade; // Adicionado para consistência

    @NotBlank(message = "O estado não pode estar em branco.")
    @Size(min = 2, max = 2, message = "O estado deve ter 2 caracteres (UF).")
    private String estado; // Adicionado para consistência

    @NotBlank(message = "O CEP não pode estar em branco.")
    @Pattern(regexp = "^\\d{8}$", message = "CEP inválido. Deve conter 8 dígitos.")
    private String cep;

    // Construtor padrão
    public EnderecoRequestDTO() {
    }

    // Construtor completo
    public EnderecoRequestDTO(String logradouro, String numero, String complemento, String bairro, String cidade, String estado, String cep) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    // Getters e Setters
    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}