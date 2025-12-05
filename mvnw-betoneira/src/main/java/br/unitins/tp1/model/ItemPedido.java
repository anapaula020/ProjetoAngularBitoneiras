package br.unitins.tp1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantidade;
    private Double precoUnitario;
    private Double totalItem;    

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "betoneira_id")
    private Betoneira betoneira;

    public ItemPedido() {
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public Double getTotalItem() {
        return totalItem;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public Betoneira getBetoneira() {
        return betoneira;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public void setTotalItem(Double totalItem) {
        this.totalItem = totalItem;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public void setBetoneira(Betoneira betoneira) {
        this.betoneira = betoneira;
    }
}