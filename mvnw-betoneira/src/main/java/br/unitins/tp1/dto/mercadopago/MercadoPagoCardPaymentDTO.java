package br.unitins.tp1.dto.mercadopago;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MercadoPagoCardPaymentDTO {

    @JsonProperty("transaction_amount")
    private Double transactionAmount;
    private String description;
    @JsonProperty("payment_method_id")
    private String paymentMethodId;
    private PayerDTO payer;
    private String token;
    private Integer installments;
    @JsonProperty("issuer_id")
    private String issuerId;

    public MercadoPagoCardPaymentDTO() {
    }

    public MercadoPagoCardPaymentDTO(Double transactionAmount, String description, String paymentMethodId, PayerDTO payer, String token, Integer installments, String issuerId) {
        this.transactionAmount = transactionAmount;
        this.description = description;
        this.paymentMethodId = paymentMethodId;
        this.payer = payer;
        this.token = token;
        this.installments = installments;
        this.issuerId = issuerId;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public PayerDTO getPayer() {
        return payer;
    }

    public void setPayer(PayerDTO payer) {
        this.payer = payer;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getInstallments() {
        return installments;
    }

    public void setInstallments(Integer installments) {
        this.installments = installments;
    }

    public String getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }
}