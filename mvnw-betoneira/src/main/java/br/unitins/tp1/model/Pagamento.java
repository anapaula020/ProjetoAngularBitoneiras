// src/main/java/br/unitins/tp1/model/Pagamento.java
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
    private StatusPagamento status; // Added status field of type StatusPagamento

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPagamento tipoPagamento;

    @Column(nullable = false)
    private Double valor;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false) // ALTERADO: de id_pedido para pedido_id
    private Pedido pedido;

    // Constructors
    public Pagamento() {
    }

    public Pagamento(LocalDateTime dataPagamento, StatusPagamento status, TipoPagamento tipoPagamento, Double valor, Pedido pedido) {
        this.dataPagamento = dataPagamento;
        this.status = status;
        this.tipoPagamento = tipoPagamento;
        this.valor = valor;
        this.pedido = pedido;
    }

    // Getters
    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public StatusPagamento getStatus() { // Added getter for status
        return status;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public Double getValor() {
        return valor;
    }

    public Pedido getPedido() {
        return pedido;
    }

    // Setters
    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public void setStatus(StatusPagamento status) { // Added setter for status
        this.status = status;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}