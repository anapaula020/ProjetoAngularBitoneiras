// src/main/java/br/unitins/tp1/dto/FabricanteRequestDTO.java
package br.unitins.tp1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class FabricanteRequestDTO {

    @NotBlank(message = "O nome do fabricante não pode ser nulo ou vazio.")
    @Size(min = 2, max = 100, message = "O nome do fabricante deve ter entre 2 e 100 caracteres.")
    private String nome;

    // Construtor padrão
    public FabricanteRequestDTO() {
    }

    // Construtor completo
    public FabricanteRequestDTO(String nome) {
        this.nome = nome;
    }

    // Getter
    public String getNome() {
        return nome;
    }

    // Setter
    public void setNome(String nome) {
        this.nome = nome;
    }
}