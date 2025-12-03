// src/main/java/br/unitins/tp1/dto/ItemPedidoResponseDTO.java
package br.unitins.tp1.dto;

// Removed unused import: import br.unitins.tp1.model.Betoneira; //
import br.unitins.tp1.model.ItemPedido; // Make sure this is used or remove if not

public class ItemPedidoResponseDTO {
    private Long id;
    private Integer quantidade;
    private Double precoUnitario;
    private String nomeBetoneira; // Assuming you want to show the name of the Betoneira

    public ItemPedidoResponseDTO() {
    }

    public ItemPedidoResponseDTO(Long id, Integer quantidade, Double precoUnitario, String nomeBetoneira) {
        this.id = id;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.nomeBetoneira = nomeBetoneira;
    }

    public static ItemPedidoResponseDTO valueOf(ItemPedido itemPedido) {
        // You might need to fetch the Betoneira from the itemPedido if it's lazy loaded
        // and then get its name. For simplicity, assuming itemPedido.getBetoneira().getNome() is accessible.
        String betoneiraName = (itemPedido.getBetoneira() != null) ? itemPedido.getBetoneira().getNome() : "N/A";
        return new ItemPedidoResponseDTO(itemPedido.getId(), itemPedido.getQuantidade(), itemPedido.getPrecoUnitario(), betoneiraName);
    }

    // Getters
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

    // Setters
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