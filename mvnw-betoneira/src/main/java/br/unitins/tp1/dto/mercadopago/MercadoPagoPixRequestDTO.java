package br.unitins.tp1.dto.mercadopago;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.Valid;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MercadoPagoPixRequestDTO {

    @NotNull
    @Positive
    @JsonProperty("transaction_amount")
    private Double transactionAmount;

    @NotNull
    private String description;

    @NotNull
    @JsonProperty("payment_method_id")
    private String paymentMethodId;

    @NotNull
    @Valid
    private PayerDTO payer;

    // Getters e Setters
    public Double getTransactionAmount() { return transactionAmount; }
    public void setTransactionAmount(Double transactionAmount) { this.transactionAmount = transactionAmount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPaymentMethodId() { return paymentMethodId; }
    public void setPaymentMethodId(String paymentMethodId) { this.paymentMethodId = paymentMethodId; }

    public PayerDTO getPayer() { return payer; }
    public void setPayer(PayerDTO payer) { this.payer = payer; }
}
