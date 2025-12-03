// src/main/java/br/unitins/tp1/model/TipoBetoneira.java
package br.unitins.tp1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class TipoBetoneira extends DefaultEntity { // Assumed to extend DefaultEntity for getId()
    @Column(nullable = false)
    private String nome;

    // Default constructor
    public TipoBetoneira() {
    }

    // Constructor with name
    public TipoBetoneira(String nome) {
        this.nome = nome;
    }

    // Getter and Setter for nome
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    // getId() is inherited from DefaultEntity
}