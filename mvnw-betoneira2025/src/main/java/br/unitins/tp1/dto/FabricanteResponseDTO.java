// src/main/java/br/unitins/tp1/dto/FabricanteResponseDTO.java
package br.unitins.tp1.dto;

import br.unitins.tp1.model.Fabricante;

public class FabricanteResponseDTO {

    private Long id;
    private String nome;

    // Construtor padrão
    public FabricanteResponseDTO() {
    }

    // Construtor completo
    public FabricanteResponseDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    // Método estático para conversão de Model para DTO
    public static FabricanteResponseDTO valueOf(Fabricante fabricante) {
        return new FabricanteResponseDTO(
            fabricante.getId(),
            fabricante.getNome()
        );
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    // Setters (geralmente não são necessários para DTOs de resposta)
    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}