package br.unitins.tp1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
// Importações de validação, se aplicável, como jakarta.validation.constraints.NotBlank etc.
// Por simplicidade, não as incluirei aqui, mas você pode adicioná-las se precisar.

public class PixDTO {

    private String id; // ID da transação do Mercado Pago
    private String status; // Status do pagamento (e.g., "approved", "pending", "rejected")
    private String qrCode; // QR Code para pagamento PIX
    private String qrCodeBase64; // QR Code em Base64
    private String ticketUrl; // URL do ticket, se houver

    // Campos adicionados para resolver os erros de 'undefined method' no PixService
    private Double amount; // Valor da transação
    private String email; // Email do pagador
    @JsonProperty("first_name") // Mapeamento para JSON se necessário na entrada
    private String firstName; // Primeiro nome do pagador
    @JsonProperty("last_name") // Mapeamento para JSON se necessário na entrada
    private String lastName; // Sobrenome do pagador
    @JsonProperty("identification_type") // Mapeamento para JSON se necessário na entrada
    private String identificationType; // Tipo de identificação (e.g., "CPF", "CNPJ")
    @JsonProperty("identification_number") // Mapeamento para JSON se necessário na entrada
    private String identificationNumber; // Número de identificação


    // --- Getters e Setters ---

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getQrCodeBase64() {
        return qrCodeBase64;
    }

    public void setQrCodeBase64(String qrCodeBase64) {
        this.qrCodeBase64 = qrCodeBase64;
    }

    public String getTicketUrl() { // Adicionado
        return ticketUrl;
    }

    public void setTicketUrl(String ticketUrl) { // Adicionado
        this.ticketUrl = ticketUrl;
    }

    public Double getAmount() { // Adicionado
        return amount;
    }

    public void setAmount(Double amount) { // Adicionado
        this.amount = amount;
    }

    public String getEmail() { // Adicionado
        return email;
    }

    public void setEmail(String email) { // Adicionado
        this.email = email;
    }

    public String getFirstName() { // Adicionado
        return firstName;
    }

    public void setFirstName(String firstName) { // Adicionado
        this.firstName = firstName;
    }

    public String getLastName() { // Adicionado
        return lastName;
    }

    public void setLastName(String lastName) { // Adicionado
        this.lastName = lastName;
    }

    public String getIdentificationType() { // Adicionado
        return identificationType;
    }

    public void setIdentificationType(String identificationType) { // Adicionado
        this.identificationType = identificationType;
    }

    public String getIdentificationNumber() { // Adicionado
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) { // Adicionado
        this.identificationNumber = identificationNumber;
    }
}