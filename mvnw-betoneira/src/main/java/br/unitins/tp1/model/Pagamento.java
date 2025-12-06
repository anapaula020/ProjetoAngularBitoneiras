
package br.unitins.tp1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
public class Pagamento extends DefaultEntity {

    @Column(nullable = false)
    private LocalDateTime dataPagamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnumStatusPagamento status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnumTipoPagamento tipoPagamento;

    @Column(nullable = false)
    private Double valor;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    public Pagamento() {
    }

    public Pagamento(LocalDateTime dataPagamento, EnumStatusPagamento status, EnumTipoPagamento tipoPagamento, Double valor, Pedido pedido) {
        this.dataPagamento = dataPagamento;
        this.status = status;
        this.tipoPagamento = tipoPagamento;
        this.valor = valor;
        this.pedido = pedido;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public EnumStatusPagamento getStatus() {
        return status;
    }

    public EnumTipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public Double getValor() {
        return valor;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public void setStatus(EnumStatusPagamento status) {
        this.status = status;
    }

    public void setTipoPagamento(EnumTipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}