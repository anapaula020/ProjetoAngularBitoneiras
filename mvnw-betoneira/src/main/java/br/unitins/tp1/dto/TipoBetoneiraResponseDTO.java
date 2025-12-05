package br.unitins.tp1.dto;

import br.unitins.tp1.model.TipoBetoneiraEntity;

public class TipoBetoneiraResponseDTO {
    private Long id;
    private String nome;

    public TipoBetoneiraResponseDTO(TipoBetoneiraEntity tipoBetoneiraEntity) {
        this.id = tipoBetoneiraEntity.getId();
        this.nome = tipoBetoneiraEntity.getNome();
    }

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