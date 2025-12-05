package br.unitins.tp1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EnderecoDTO {

    @NotBlank(message = "O logradouro não pode ser nulo ou vazio.")
    @Size(max = 200, message = "Logradouro deve ter no máximo 200 caracteres.")
    private String logradouro;

    @NotBlank(message = "O número não pode ser nulo ou vazio.")
    @Size(max = 20, message = "Número deve ter no máximo 20 caracteres.")
    private String numero;

    @Size(max = 100, message = "Complemento deve ter no máximo 100 caracteres.")
    private String complemento;

    @NotBlank(message = "O bairro não pode ser nulo ou vazio.")
    @Size(max = 100, message = "Bairro deve ter no máximo 100 caracteres.")
    private String bairro;

    @NotBlank(message = "O CEP não pode ser nulo ou vazio.")
    @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos numéricos.")
    private String cep;

    @NotBlank(message = "O município não pode ser nulo ou vazio.")
    @Size(max = 100, message = "Município deve ter no máximo 100 caracteres.")
    private String municipio;

    public EnderecoDTO() {
    }

    public EnderecoDTO(String logradouro, String numero, String complemento, String bairro, String cep, String municipio) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cep = cep;
        this.municipio = municipio;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCep() {
        return cep;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }
}