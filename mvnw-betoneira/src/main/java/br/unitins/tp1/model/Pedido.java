package br.unitins.tp1.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Pedido extends DefaultEntity {
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
    @OneToMany(cascade = CascadeType.DETACH)
    @JoinColumn(name = "id_itens")
    private List<ItemPedido> itens;
    @Column
    private Double preco;
    private Endereco endereco;
    @Column(nullable = true)
    private EnumTipoPagamento tipoPagamento;
    @Column(nullable = false)
    private EnumStatusPedido statusPedido;

    public Pedido() {
    }

    public Pedido(Cliente cliente, List<ItemPedido> itens, Double preco, Endereco endereco,
            EnumTipoPagamento tipoPagamento, EnumStatusPedido statusPedido) {
        this.cliente = cliente;
        this.itens = itens;
        this.preco = preco;
        this.endereco = endereco;
        this.tipoPagamento = tipoPagamento;
        this.statusPedido = statusPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public EnumTipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(EnumTipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public EnumStatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(EnumStatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }

}