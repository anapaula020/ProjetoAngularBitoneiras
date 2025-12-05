package br.unitins.tp1.dto;

import br.unitins.tp1.model.Fabricante;

public class FabricanteResponseDTO {

    private Long id;
    private String nome;

    public FabricanteResponseDTO() {
    }

    public FabricanteResponseDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public static FabricanteResponseDTO valueOf(Fabricante fabricante) {
        return new FabricanteResponseDTO(
            fabricante.getId(),
            fabricante.getNome()
        );
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}