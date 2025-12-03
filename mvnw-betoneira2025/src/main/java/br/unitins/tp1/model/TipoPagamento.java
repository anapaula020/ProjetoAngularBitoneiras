// src/main/java/br/unitins/tp1/model/TipoPagamento.java
package br.unitins.tp1.model;

public enum TipoPagamento {
    PIX(1, "PIX"),
    CARTAO_CREDITO(2, "Cartão de Crédito"),
    BOLETO(3, "Boleto");

    private final int id;
    private final String label;

    TipoPagamento(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    // Method to get enum from ID
    public static TipoPagamento valueOf(int id) {
        for (TipoPagamento tipo : TipoPagamento.values()) {
            if (tipo.getId() == id) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("ID de TipoPagamento inválido: " + id);
    }

    // Method to get enum from String label (case-insensitive)
    public static TipoPagamento valueOfLabel(String label) {
        for (TipoPagamento tipo : TipoPagamento.values()) {
            if (tipo.getLabel().equalsIgnoreCase(label)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Label de TipoPagamento inválido: " + label);
    }
}