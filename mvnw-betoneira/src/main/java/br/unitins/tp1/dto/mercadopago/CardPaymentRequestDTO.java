package br.unitins.tp1.dto.mercadopago;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CardPaymentRequestDTO {

    @NotBlank(message = "O número do cartão não pode estar em branco.")
    @Size(min = 13, max = 19, message = "O número do cartão deve ter entre 13 e 19 dígitos.")
    private String cardNumber;

    @NotBlank(message = "O código de segurança não pode estar em branco.")
    @Size(min = 3, max = 4, message = "O código de segurança deve ter 3 ou 4 dígitos.")
    private String securityCode;

    @NotBlank(message = "A data de vencimento não pode estar em branco.")
    @Pattern(regexp = "^(0[1-9]|1[0-2])\\/([0-9]{2})$", message = "A data de vencimento deve estar no formato MM/AA.")
    private String expirationDate;

    @NotBlank(message = "O nome do titular do cartão não pode estar em branco.")
    private String cardHolderName;

    @NotNull(message = "O valor do pagamento não pode ser nulo.")
    private Double amount;

    public CardPaymentRequestDTO() {
    }

    public CardPaymentRequestDTO(String cardNumber, String securityCode, String expirationDate, String cardHolderName, Double amount) {
        this.cardNumber = cardNumber;
        this.securityCode = securityCode;
        this.expirationDate = expirationDate;
        this.cardHolderName = cardHolderName;
        this.amount = amount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}