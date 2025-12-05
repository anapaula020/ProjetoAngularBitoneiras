package br.unitins.tp1.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoRequestDTO {

    private LocalDateTime dataPedido;

    @NotNull(message = "A lista de itens do pedido não pode ser nula.")
    private List<ItemPedidoRequestDTO> itens;

    @NotNull(message = "O ID do endereço de entrega não pode ser nulo.")
    private Long idEnderecoEntrega;

    public PedidoRequestDTO() {
    }

    public PedidoRequestDTO(LocalDateTime dataPedido, List<ItemPedidoRequestDTO> itens, Long idEnderecoEntrega) {
        this.dataPedido = dataPedido;
        this.itens = itens;
        this.idEnderecoEntrega = idEnderecoEntrega;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public List<ItemPedidoRequestDTO> getItens() {
        return itens;
    }

    public Long getIdEnderecoEntrega() {
        return idEnderecoEntrega;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public void setItens(List<ItemPedidoRequestDTO> itens) {
        this.itens = itens;
    }

    public void setIdEnderecoEntrega(Long idEnderecoEntrega) {
        this.idEnderecoEntrega = idEnderecoEntrega;
    }
}