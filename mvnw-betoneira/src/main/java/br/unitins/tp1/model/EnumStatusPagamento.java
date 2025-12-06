package br.unitins.tp1.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumStatusPagamento {
    APROVADO(1, "Aprovado"),
    PROCESSANDO(1, "Processando"),
    REJEITADO(1, "Rejeitado"),
    CANCELADO(1, "Cancelado");

    private final Integer id;
    private final String label;
    
    EnumStatusPagamento(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public static EnumStatusPagamento valueOf(Integer id) {
        if (id == null){
            return null;
        }
        for (EnumStatusPagamento enumStatusPagamento : EnumStatusPagamento.values()) {
            if (enumStatusPagamento.getId().equals(id)){
                return enumStatusPagamento;
            }
        }
        throw new IllegalArgumentException("Id inválido");
    }
}