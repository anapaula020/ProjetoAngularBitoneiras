package br.unitins.tp1.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PedidoDTO(
        @Size(min = 6, message = "Endereço está pequeno demais.") @Size(max = 60, message = "Endereço está grande demais.") EnderecoDTO endereco,
        @NotNull List<ItemPedidoDTO> itens) {
}