package br.unitins.tp1.dto;

import jakarta.validation.constraints.NotNull;

public class PagamentoRequestDTO {
    @NotNull(message = "O ID do pedido não pode ser nulo.")
    private Long idPedido;

    // O campo 'valor' foi removido, pois será calculado automaticamente.
    @NotNull(message = "O ID do tipo de pagamento não pode ser nulo.")
    private Integer idTipoPagamento;

    public PagamentoRequestDTO() {
    }

    // Construtor atualizado sem o campo valor
    public PagamentoRequestDTO(Long idPedido, Integer idTipoPagamento) {
        this.idPedido = idPedido;
        this.idTipoPagamento = idTipoPagamento;
    }

    // Getters
    public Long getIdPedido() {
        return idPedido;
    }

    // Setter
    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Integer getIdTipoPagamento() {
        return idTipoPagamento;
    }

    public void setIdTipoPagamento(Integer idTipoPagamento) {
        this.idTipoPagamento = idTipoPagamento;
    }
}