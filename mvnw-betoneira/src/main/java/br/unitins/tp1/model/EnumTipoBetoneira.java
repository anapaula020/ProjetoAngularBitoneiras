package br.unitins.tp1.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumTipoBetoneira {
    MISTURADOR(1, "Misturador"),
    CONCRETO(2, "Concreto"),
    PORTATIL(3, "Portatil"),
    FIXA(4, "Fixa");

    private final Integer id;
    private final String label;
    
    EnumTipoBetoneira(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public static EnumTipoBetoneira valueOf(Integer id) {
        if (id == null){
            return null;
        }
        for (EnumTipoBetoneira enumStatusPagamento : EnumTipoBetoneira.values()) {
            if (enumStatusPagamento.getId().equals(id)){
                return enumStatusPagamento;
            }
        }
        throw new IllegalArgumentException("Id inválido");
    }
}