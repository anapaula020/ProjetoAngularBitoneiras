package br.unitins.tp1.dto.mercadopago;

import jakarta.validation.constraints.NotBlank;

public class IdentificationDTO {

    @NotBlank
    private String type; // "CPF" ou "CNPJ"

    @NotBlank
    private String number;

    // Getters e Setters
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
}
