package br.unitins.tp1.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ItemPedidoRequestDTO {

    @NotNull(message = "O ID da betoneira não pode ser nulo.")
    private Long idBetoneira; // ID da betoneira

    @NotNull(message = "A quantidade não pode ser nula.")
    @Positive(message = "A quantidade deve ser um valor positivo.")
    private Integer quantidade;

    @NotNull(message = "O preço unitário não pode ser nulo.")
    @Positive(message = "O preço unitário deve ser positivo.")
    private Double precoUnitario;

    // Construtor padrão
    public ItemPedidoRequestDTO() {
    }

    // Construtor completo
    public ItemPedidoRequestDTO(Long idBetoneira, Integer quantidade, Double precoUnitario) {
        this.idBetoneira = idBetoneira;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    // Getters
    public Long getIdBetoneira() {
        return idBetoneira;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    // Setters
    public void setIdBetoneira(Long idBetoneira) {
        this.idBetoneira = idBetoneira;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}