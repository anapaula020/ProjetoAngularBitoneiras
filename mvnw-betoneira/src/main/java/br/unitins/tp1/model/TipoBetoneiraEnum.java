package br.unitins.tp1.model;

public enum TipoBetoneiraEnum {
    MISTURADOR(1),
    CONCRETO(2),
    PORTATIL(3),
    FIXA(4);

    private final int id;

    TipoBetoneiraEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    // --- NOVO MÉTODO fromId ---
    public static TipoBetoneiraEnum fromId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo.");
        }
        for (TipoBetoneiraEnum tipo : TipoBetoneiraEnum.values()) {
            if (tipo.getId() == id) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("ID de TipoBetoneiraEnum inválido: " + id);
    }
}