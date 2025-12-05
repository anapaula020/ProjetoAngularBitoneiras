package mssaat.org.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum PagamentoEstado {
    PENDENTE(1, "Pendente"),
    APROVADO(2, "Aprovado"),
    RECUSADO(3, "Recusado"),
    REEMBOLSADO(4, "Reembolsado"), 
    CANCELADO(5, "Cancelado"),
    PARCELAS(6, "Parcelas");

    private int id;
    private String nome;

    private PagamentoEstado(int id, String nome) {
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

    public static PagamentoEstado valueOf(Integer id) {
        for (PagamentoEstado sexo : PagamentoEstado.values()) {
            if (sexo.id == id) {
                return sexo;
            }
        }
        throw new IllegalArgumentException("PagamentoTipo inválido: \"" + id + "\" não é 1, 2, 3, 4.");
    }
}