package br.unitins.tp1.dto;

import br.unitins.tp1.model.Betoneira;

public record BetoneiraResponseDTO(Long id, String nome, String descricao, Integer estoque, Double preco, String tipo, FabricanteResponseDTO idFabricante) {
    public static BetoneiraResponseDTO valueOf(Betoneira betoneira){
        return new BetoneiraResponseDTO(betoneira.getId(), betoneira.getNome(), betoneira.getDescricao(), betoneira.getQuantidadeEstoque(), betoneira.getPreco(), betoneira.getTipo(), FabricanteResponseDTO.valueOf(betoneira.getFabricante()));
    }
}