package mssaat.org.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Sexo {
    FEMININO(1, "Feminino"),
    MASCULINO(2, "Masculino");
    
    private int id;
    private String nome;

    private Sexo(int id, String nome) {
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

    public static Sexo valueOf(Integer id) {
        if (id == null) {
            return null;
        }
        for (Sexo sexo : Sexo.values()) {
            if (sexo.id == id) {
                return sexo;
            }
        }
        throw new IllegalArgumentException("Sexo inválido: \"" + id + "\" não é 1 nem 2.");
    }

    public static Sexo fromId(Integer id) {
        for (Sexo sexo : values()) {
            if (sexo.getId() == id) {
                return sexo;
            }
        }
        throw new IllegalArgumentException("Invalid Sexo id: " + id);
    }
}