package br.unitins.tp1.dto.mercadopago;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PointOfInteractionDTO {

    @JsonProperty("transaction_data")
    private TransactionDataDTO transactionData;

    @JsonProperty("transaction_data")
    public TransactionDataDTO getTransactionData() {
        return transactionData;
    }

    public void setTransactionData(TransactionDataDTO transactionData) {
        this.transactionData = transactionData;
    }
}