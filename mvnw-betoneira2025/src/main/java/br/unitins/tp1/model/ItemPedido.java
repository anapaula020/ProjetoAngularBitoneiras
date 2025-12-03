package br.unitins.tp1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
// ... outras importações que você já tem

@Entity // Ou sua anotação de entidade
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantidade;
    private Double precoUnitario; // Adicione este campo se não existir
    private Double totalItem;     // Adicione este campo se não existir (ou será calculado)

    @ManyToOne
    @JoinColumn(name = "pedido_id") // Confirme o nome da coluna de chave estrangeira
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "betoneira_id") // Confirme o nome da coluna de chave estrangeira
    private Betoneira betoneira;

    // Construtor padrão
    public ItemPedido() {
    }

    // --- GETTERS NECESSÁRIOS (Adicione/Confirme a existência de todos estes) ---
    public Long getId() {
        return id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Double getPrecoUnitario() { // MÉTODO FALTANTE (Erro em ItemPedidoResponseDTO)
        return precoUnitario;
    }

    public Double getTotalItem() { // MÉTODO FALTANTE (Erro em ItemPedidoResponseDTO e PedidoService)
        // Se 'totalItem' é um campo que você persiste:
        return totalItem;
        // OU, se 'totalItem' é sempre calculado e não persistido:
        // if (this.precoUnitario != null && this.quantidade != null) {
        //     return this.precoUnitario * this.quantidade;
        // }
        // return 0.0; // Ou lance uma exceção, ou retorne null
    }

    public Pedido getPedido() { // MÉTODO JÁ EXISTENTE (Mas verifique)
        return pedido;
    }

    public Betoneira getBetoneira() { // MÉTODO JÁ EXISTENTE (Mas verifique)
        return betoneira;
    }

    // --- SETTERS (Adicione/Confirme a existência) ---
    public void setId(Long id) {
        this.id = id;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public void setTotalItem(Double totalItem) { // Adicione este setter se você persistir totalItem
        this.totalItem = totalItem;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public void setBetoneira(Betoneira betoneira) {
        this.betoneira = betoneira;
    }
}