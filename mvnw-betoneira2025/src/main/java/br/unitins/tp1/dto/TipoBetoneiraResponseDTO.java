// src/main/java/br/unitins/tp1/dto/TipoBetoneiraResponseDTO.java
package br.unitins.tp1.dto;

import br.unitins.tp1.model.TipoBetoneiraEntity; // Changed import to TipoBetoneiraEntity

public class TipoBetoneiraResponseDTO {
    private Long id;
    private String nome;

    // Updated constructor to accept TipoBetoneiraEntity
    public TipoBetoneiraResponseDTO(TipoBetoneiraEntity tipoBetoneiraEntity) {
        this.id = tipoBetoneiraEntity.getId();
        this.nome = tipoBetoneiraEntity.getNome();
    }

    // Updated valueOf method to accept TipoBetoneiraEntity
    public static TipoBetoneiraResponseDTO valueOf(TipoBetoneiraEntity tipoBetoneiraEntity) {
        return new TipoBetoneiraResponseDTO(tipoBetoneiraEntity);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}