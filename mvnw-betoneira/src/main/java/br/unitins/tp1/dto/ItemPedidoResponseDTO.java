package br.unitins.tp1.dto;

import br.unitins.tp1.model.ItemPedido;

public record ItemPedidoResponseDTO(Long id,BetoneiraResponseDTO betoneira ,String nome,  Double preco, Double desconto, Integer quantidade) {
    public static ItemPedidoResponseDTO valueOf(ItemPedido item) {
        return new ItemPedidoResponseDTO(item.getId(), new BetoneiraResponseDTO(item.getBetoneira().getId(), item.getBetoneira().getNome(), item.getBetoneira().getDescricao(), item.getBetoneira().getQuantidadeEstoque(), item.getBetoneira().getPreco(), item.getBetoneira().getTipo(), FabricanteResponseDTO.valueOf(item.getBetoneira().getFabricante())),item.getBetoneira().getNome(), item.getPreco(), item.getDesconto(), item.getQuantidade());
    }
}