// src/main/java/br/unitins/tp1/model/TipoBetoneiraEntity.java
package br.unitins.tp1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class TipoBetoneiraEntity extends DefaultEntity { // Extends DefaultEntity for getId()
    @Column(nullable = false)
    private String nome;

    // Default constructor
    public TipoBetoneiraEntity() {
    }

    // Constructor with name
    public TipoBetoneiraEntity(String nome) {
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