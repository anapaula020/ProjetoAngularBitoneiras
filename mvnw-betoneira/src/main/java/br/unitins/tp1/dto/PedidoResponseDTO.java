package br.unitins.tp1.dto;

import java.util.List;

import br.unitins.tp1.model.EnumStatusPedido;
import br.unitins.tp1.model.EnumTipoPagamento;
import br.unitins.tp1.model.Pedido;
import br.unitins.tp1.model.Cliente;

public record PedidoResponseDTO(Long id, Cliente cliente, EnderecoResponseDTO endereco, List<ItemPedidoResponseDTO> itens,Double preco, EnumStatusPedido statusPedido, EnumTipoPagamento tipoPagamento) {
    public static PedidoResponseDTO valueOf(Pedido pedido) {
        if (pedido == null) {
            return null;
        }

        List<ItemPedidoResponseDTO> pedidos = pedido.getItens()
                .stream()
                .map(ItemPedidoResponseDTO::valueOf)
                .toList();

        return new PedidoResponseDTO(pedido.getId(), pedido.getCliente(), EnderecoResponseDTO.valueOf(pedido.getEndereco()), pedidos, pedido.getPreco(), pedido.getStatusPedido(), pedido.getTipoPagamento());
    }
}