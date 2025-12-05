package br.unitins.tp1.dto;

import br.unitins.tp1.model.ItemPedido;

public class ItemPedidoResponseDTO {
    private Long id;
    private Integer quantidade;
    private Double precoUnitario;
    private String nomeBetoneira;

    public ItemPedidoResponseDTO() {
    }

    public ItemPedidoResponseDTO(Long id, Integer quantidade, Double precoUnitario, String nomeBetoneira) {
        this.id = id;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.nomeBetoneira = nomeBetoneira;
    }

    public static ItemPedidoResponseDTO valueOf(ItemPedido itemPedido) {
        String betoneiraName = (itemPedido.getBetoneira() != null) ? itemPedido.getBetoneira().getNome() : "N/A";
        return new ItemPedidoResponseDTO(itemPedido.getId(), itemPedido.getQuantidade(), itemPedido.getPrecoUnitario(), betoneiraName);
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

    public String getNomeBetoneira() {
        return nomeBetoneira;
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

    public void setNomeBetoneira(String nomeBetoneira) {
        this.nomeBetoneira = nomeBetoneira;
    }
}