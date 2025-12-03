package br.unitins.tp1.dto.mercadopago;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionDataDTO {

    @JsonProperty("qr_code")
    private String qrCode; // Pix Copia e Cola

    @JsonProperty("qr_code_base64")
    private String qrCodeBase64; // QR code em Base64

    @JsonProperty("ticket_url") // Pode ser útil para outros tipos de pagamento ou recibos
    private String ticketUrl;

    // Adicione outros campos de transaction_data se necessário da documentação do Mercado Pago

    @JsonProperty("qr_code")
    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    @JsonProperty("qr_code_base64")
    public String getQrCodeBase64() {
        return qrCodeBase64;
    }

    public void setQrCodeBase64(String qrCodeBase64) {
        this.qrCodeBase64 = qrCodeBase64;
    }

    @JsonProperty("ticket_url")
    public String getTicketUrl() {
        return ticketUrl;
    }

    public void setTicketUrl(String ticketUrl) {
        this.ticketUrl = ticketUrl;
    }
}