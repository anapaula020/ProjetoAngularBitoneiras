// src/main/java/br/unitins/tp1/dto/PedidoRequestDTO.java
package br.unitins.tp1.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoRequestDTO {

    private LocalDateTime dataPedido; // Pode ser gerado no backend

    @NotNull(message = "A lista de itens do pedido não pode ser nula.")
    private List<ItemPedidoRequestDTO> itens; // Lista de DTOs para os itens do pedido

    @NotNull(message = "O ID do endereço de entrega não pode ser nulo.") // New validation
    private Long idEnderecoEntrega; // New field for delivery address ID

    // Construtor padrão
    public PedidoRequestDTO() {
    }

    // Construtor completo
    public PedidoRequestDTO(LocalDateTime dataPedido, List<ItemPedidoRequestDTO> itens, Long idEnderecoEntrega) { // Updated constructor
        this.dataPedido = dataPedido;
        this.itens = itens;
        this.idEnderecoEntrega = idEnderecoEntrega; // New field
    }

    // Getters
    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public List<ItemPedidoRequestDTO> getItens() {
        return itens;
    }

    public Long getIdEnderecoEntrega() { // New getter
        return idEnderecoEntrega;
    }

    // Setters
    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public void setItens(List<ItemPedidoRequestDTO> itens) {
        this.itens = itens;
    }

    public void setIdEnderecoEntrega(Long idEnderecoEntrega) { // New setter
        this.idEnderecoEntrega = idEnderecoEntrega;
    }
}