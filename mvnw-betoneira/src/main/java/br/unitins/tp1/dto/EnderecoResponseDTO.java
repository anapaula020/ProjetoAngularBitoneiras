package br.unitins.tp1.dto;

import br.unitins.tp1.model.Endereco;

public record EnderecoResponseDTO(
    String numero, 
    String complemento, 
    String bairro, 
    String cidade, 
    String estado, 
    String cep
) {
    public static EnderecoResponseDTO valueOf(Endereco endereco) {
        return new EnderecoResponseDTO(
            endereco.getNumero(), 
            endereco.getComplemento(), 
            endereco.getBairro(), 
            endereco.getCidade(), 
            endereco.getEstado(), 
            endereco.getCep()
        );
    }
}