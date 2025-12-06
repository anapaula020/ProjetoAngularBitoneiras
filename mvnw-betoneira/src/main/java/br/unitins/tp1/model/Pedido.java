package br.unitins.tp1.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pedido extends DefaultEntity {

    @Column(nullable = false)
    private LocalDateTime dataDoPedido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnumStatusPedido statusPedido; 

    @Column(nullable = false)
    private Double totalPedido; 

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false) 
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "endereco_entrega_id", nullable = false) 
    private Endereco enderecoEntrega;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens;

    public Pedido() {
        this.itens = new ArrayList<>();
        this.dataDoPedido = LocalDateTime.now(); 
        this.statusPedido = EnumStatusPedido.PENDENTE; 
    }

    public Pedido(LocalDateTime dataDoPedido, EnumStatusPedido statusPedido, Double totalPedido, Cliente cliente, Endereco enderecoEntrega, List<ItemPedido> itens) {
        this.dataDoPedido = dataDoPedido;
        this.statusPedido = statusPedido;
        this.totalPedido = totalPedido;
        this.cliente = cliente;
        this.enderecoEntrega = enderecoEntrega;
        this.itens = itens != null ? itens : new ArrayList<>();
    }

    public LocalDateTime getDataDoPedido() {
        return dataDoPedido;
    }

    public EnumStatusPedido getStatusPedido() { 
        return statusPedido;
    }

    public Double getTotalPedido() {
        return totalPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Endereco getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    
    public void setDataDoPedido(LocalDateTime dataDoPedido) {
        this.dataDoPedido = dataDoPedido;
    }

    public void setStatusPedido(EnumStatusPedido statusPedido) { 
        this.statusPedido = statusPedido;
    }

    public void setTotalPedido(Double totalPedido) {
        this.totalPedido = totalPedido;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setEnderecoEntrega(Endereco enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }
}