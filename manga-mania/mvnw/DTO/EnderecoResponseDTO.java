package mssaat.org.DTO;

import mssaat.org.model.Endereco;

public record EnderecoResponseDTO(
    String rua,
    String numero,
    String cidade,
    String estado,
    String cep
) {
    public static EnderecoResponseDTO valueOf(Endereco endereco) {
        return new EnderecoResponseDTO(
            endereco.getRua(),
            endereco.getNumero(),
            endereco.getCidade(),
            endereco.getEstado(),
            endereco.getCep()
        );
    }
}