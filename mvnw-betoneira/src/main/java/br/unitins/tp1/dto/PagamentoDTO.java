package br.unitins.tp1.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

public class PagamentoDTO {

    @NotNull(message = "O valor do pagamento não pode ser nulo.")
    @Positive(message = "O valor do pagamento deve ser um valor positivo.")
    private Double valor;

    private String statusPagamento;

    private LocalDateTime dataPagamento;

    @NotNull(message = "O ID do tipo de pagamento não pode ser nulo.")
    private Long idTipoPagamento;

    public PagamentoDTO() {
    }

    public PagamentoDTO(Double valor, String statusPagamento, LocalDateTime dataPagamento, Long idTipoPagamento) {
        this.valor = valor;
        this.statusPagamento = statusPagamento;
        this.dataPagamento = dataPagamento;
        this.idTipoPagamento = idTipoPagamento;
    }

    public Double getValor() {
        return valor;
    }

    public String getStatusPagamento() {
        return statusPagamento;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public Long getIdTipoPagamento() {
        return idTipoPagamento;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setStatusPagamento(String statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public void setIdTipoPagamento(Long idTipoPagamento) {
        this.idTipoPagamento = idTipoPagamento;
    }
}