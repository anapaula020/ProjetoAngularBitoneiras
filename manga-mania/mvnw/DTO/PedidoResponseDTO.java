package mssaat.org.DTO;

import java.util.List;

import mssaat.org.model.Endereco;
import mssaat.org.model.ItemPedido;
import mssaat.org.model.Manga;
import mssaat.org.model.PagamentoEstado;
import mssaat.org.model.PagamentoTipo;
import mssaat.org.model.Pedido;
import mssaat.org.model.Usuario;

public record PedidoResponseDTO(Long id, Usuario usuario, EnderecoResponseDTO endereco, List<ItemPedidoResponseDTO> itens,Double preco, PagamentoEstado estadoPagamento, PagamentoTipo tipoPagamento) {
    public static PedidoResponseDTO valueOf(Pedido pedido) {
        if (pedido == null) {
            return null;
        }

        List<ItemPedidoResponseDTO> mangas = pedido.getItens()
                .stream()
                .map(ItemPedidoResponseDTO::valueOf)
                .toList();

        return new PedidoResponseDTO(pedido.getId(), pedido.getUsuario(), EnderecoResponseDTO.valueOf(pedido.getEndereco()), mangas, pedido.getPreco(), pedido.getEstadoPagamento(), pedido.getTipoPagamento());
    }
}