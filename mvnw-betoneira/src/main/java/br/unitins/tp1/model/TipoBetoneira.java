package br.unitins.tp1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class TipoBetoneira extends DefaultEntity {
    @Column(nullable = false)
    private String nome;

   
    public TipoBetoneira() {
    }

   
    public TipoBetoneira(String nome) {
        this.nome = nome;
    }

   
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}