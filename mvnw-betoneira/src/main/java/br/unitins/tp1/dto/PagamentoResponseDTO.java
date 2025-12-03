// src/main/java/br/unitins/tp1/dto/PagamentoResponseDTO.java
package br.unitins.tp1.dto;

import br.unitins.tp1.model.Pagamento;
// Removed unused import: import br.unitins.tp1.model.StatusPagamento;
// Removed unused import: import br.unitins.tp1.model.TipoPagamento;

import java.time.LocalDateTime;

public class PagamentoResponseDTO {
    private Long id;
    private LocalDateTime dataPagamento;
    private String statusPagamento;
    private String tipoPagamento;
    private Double valor;
    private Long idPedido;

    public PagamentoResponseDTO(Pagamento pagamento) {
        this.id = pagamento.getId();
        this.dataPagamento = pagamento.getDataPagamento();
        this.statusPagamento = pagamento.getStatus().name();
        this.tipoPagamento = pagamento.getTipoPagamento().name();
        this.valor = pagamento.getValor();
        this.idPedido = pagamento.getPedido().getId();
    }

    public static PagamentoResponseDTO valueOf(Pagamento pagamento) {
        return new PagamentoResponseDTO(pagamento);
    }

    // Getters
    public Long getId() {
        return id;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public String getStatusPagamento() {
        return statusPagamento;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public Double getValor() {
        return valor;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    // Setters (optional for DTOs)
    public void setId(Long id) {
        this.id = id;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public void setStatusPagamento(String statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }
}