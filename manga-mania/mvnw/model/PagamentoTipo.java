package mssaat.org.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum PagamentoTipo {
    CREDITO(1, "Credito"),
    DEBITO(2, "Debito"),
    PIX(3, "PIX");

    private int id;
    private String nome;

    private PagamentoTipo(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public static PagamentoTipo valueOf(Integer id) {
        for (PagamentoTipo pagamento : PagamentoTipo.values()) {
            if (pagamento.id == id) {
                return pagamento;
            }
        }
        throw new IllegalArgumentException("PagamentoTipo inválido: \"" + id + "\" não é 1, 2, 3, 4.");
    }
}