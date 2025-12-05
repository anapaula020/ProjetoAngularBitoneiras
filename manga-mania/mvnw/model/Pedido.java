package mssaat.org.model;

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
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.DETACH)
    @JoinColumn(name = "id_itens")
    private List<ItemPedido> itens;
    @Column
    private Double preco;
    
    private Endereco endereco;
    @Column(nullable = true)
    private PagamentoTipo tipoPagamento;
    @Column(nullable = false)
    private PagamentoEstado estadoPagamento;

    public PagamentoTipo getTipoPagamento() {
        return tipoPagamento;
    }
    public void setTipoPagamento(PagamentoTipo tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public PagamentoEstado getEstadoPagamento() {
        return estadoPagamento;
    }   
    public void setEstadoPamento(PagamentoEstado estadoPagamento) {
        this.estadoPagamento = estadoPagamento;
    }
    public Pedido() {
    }

    public Pedido(Usuario usuario, Endereco endereco, List<ItemPedido> itens) {
        this.usuario = usuario;
        this.endereco = endereco;
        this.itens = itens;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

}