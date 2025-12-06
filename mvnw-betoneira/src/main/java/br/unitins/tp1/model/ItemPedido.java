package br.unitins.tp1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class ItemPedido extends DefaultEntity {
    @ManyToOne
    @JoinColumn(name = "id_betoneira")
    private Betoneira betoneira;
    private Double preco;
    private Double desconto;
    private Integer quantidade;

    public ItemPedido() {
    }

    public ItemPedido(Betoneira betoneira, Double preco, Double desconto, Integer quantidade) {
        this.betoneira = betoneira;
        this.preco = preco;
        this.desconto = desconto;
        this.quantidade = quantidade;
    }

    public Betoneira getBetoneira() {
        return betoneira;
    }

    public void setBetoneira(Betoneira betoneira) {
        this.betoneira = betoneira;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}