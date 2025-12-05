package mssaat.org.DTO;

import mssaat.org.DTO.MangaResponseDTO.SimpleMangaResponseDTO;
import mssaat.org.model.ItemPedido;
import mssaat.org.model.Manga;

public record ItemPedidoResponseDTO(Long id,SimpleMangaResponseDTO manga ,String nome,  Double preco, Double desconto, Integer quantidade) {
    public static ItemPedidoResponseDTO valueOf(ItemPedido item) {
        return new ItemPedidoResponseDTO(item.getId(), new SimpleMangaResponseDTO(item.getManga().getId(),item.getManga().getNome()),item.getManga().getNome(), item.getPreco(), item.getDesconto(), item.getQuantidade());
    }
}