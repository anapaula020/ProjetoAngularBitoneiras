package br.unitins.tp1.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumTipoPagamento {
    PIX(1, "PIX"),
    CARTAO_CREDITO(2, "Cartão de Crédito"),
    BOLETO(3, "Boleto");

    private final Integer id;
    private final String label;
    
    EnumTipoPagamento(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public static EnumTipoPagamento valueOf(Integer id) {
        if (id == null){
            return null;
        }
        for (EnumTipoPagamento enumStatusPagamento : EnumTipoPagamento.values()) {
            if (enumStatusPagamento.getId().equals(id)){
                return enumStatusPagamento;
            }
        }
        throw new IllegalArgumentException("Id inválido");
    }
}