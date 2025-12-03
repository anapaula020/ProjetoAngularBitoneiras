// src/main/java/br/unitins/tp1/dto/TipoBetoneiraDTO.java
package br.unitins.tp1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TipoBetoneiraDTO {

    @NotBlank(message = "O nome do tipo de betoneira não pode ser nulo ou vazio.")
    @Size(min = 2, max = 100, message = "O nome do tipo deve ter entre 2 e 100 caracteres.")
    private String nome;

    private String descricao;

    // Construtor padrão
    public TipoBetoneiraDTO() {
    }

    // Construtor completo
    public TipoBetoneiraDTO(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}