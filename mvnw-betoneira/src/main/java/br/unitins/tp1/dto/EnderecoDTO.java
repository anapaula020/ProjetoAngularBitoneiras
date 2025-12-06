package br.unitins.tp1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EnderecoDTO {
    @NotBlank(message = "O número não pode ser nulo ou vazio.")
    @Size(max = 20, message = "Número deve ter no máximo 20 caracteres.")
    private String numero;

    @Size(max = 100, message = "Complemento deve ter no máximo 100 caracteres.")
    private String complemento;

    @NotBlank(message = "O bairro não pode ser nulo ou vazio.")
    @Size(max = 100, message = "Bairro deve ter no máximo 100 caracteres.")
    private String bairro;

    @NotBlank(message = "A rua não pode ser nulo ou vazio.")
    @Size(max = 100, message = "A rua deve ter no máximo 100 caracteres.")
    private String rua;

    @NotBlank(message = "O CEP não pode ser nulo ou vazio.")
    @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos numéricos.")
    private String cep;

    @NotBlank(message = "O município não pode ser nulo ou vazio.")
    @Size(max = 100, message = "Município deve ter no máximo 100 caracteres.")
    private String municipio;

    public EnderecoDTO() {
    }

    public EnderecoDTO(String numero, String complemento, String bairro, String rua, String cep, String municipio) {
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.rua = rua;
        this.cep = cep;
        this.municipio = municipio;
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

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }
}