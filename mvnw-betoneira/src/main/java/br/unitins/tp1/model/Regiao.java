package br.unitins.tp1.model;

public enum Regiao {
    NORTE(1),
    NORDESTE(2),
    CENTRO_OESTE(3),
    SUDESTE(4),
    SUL(5);

    private final int value;

    Regiao(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Regiao valueOf(int value) {
        for (Regiao regiao : Regiao.values()) {
            if (regiao.getValue() == value) {
                return regiao;
            }
        }
        throw new IllegalArgumentException("Invalid Regiao value: " + value);
    }
}