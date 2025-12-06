package br.unitins.tp1.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumStatusPedido {
    PENDENTE(1, "Pendente"),
    PAGO(2, "Pago"),
    CANCELADO(3, "Cancelado"),
    ENVIADO(4, "Enviado"),
    ENTREGUE(5, "Entregue"),
    PROCESSANDO(6, "Processando");

    private final Integer id;
    private final String label;
    
    EnumStatusPedido(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public static EnumStatusPedido valueOf(Integer id) {
        if (id == null){
            return null;
        }
        for (EnumStatusPedido enumStatusPagamento : EnumStatusPedido.values()) {
            if (enumStatusPagamento.getId().equals(id)){
                return enumStatusPagamento;
            }
        }
        throw new IllegalArgumentException("Id inválido");
    }
}