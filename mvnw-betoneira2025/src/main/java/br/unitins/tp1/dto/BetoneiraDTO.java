// src/main/java/br/unitins/tp1/dto/BetoneiraDTO.java
package br.unitins.tp1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class BetoneiraDTO {

    @NotBlank(message = "O nome não pode ser nulo ou vazio.")
    private String nome;

    private String descricao;

    @NotNull(message = "O preço não pode ser nulo.")
    @Positive(message = "O preço deve ser um valor positivo.")
    private Double preco;

    @NotNull(message = "O ID do tipo de betoneira não pode ser nulo.")
    private Long idTipoBetoneira;

    @NotNull(message = "O ID do fabricante não pode ser nulo.")
    private Long idFabricante;

    // Construtor padrão
    public BetoneiraDTO() {
    }

    // Construtor completo
    public BetoneiraDTO(String nome, String descricao, Double preco, Long idTipoBetoneira, Long idFabricante) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.idTipoBetoneira = idTipoBetoneira;
        this.idFabricante = idFabricante;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public Long getIdTipoBetoneira() {
        return idTipoBetoneira;
    }

    public Long getIdFabricante() {
        return idFabricante;
    }

    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public void setIdTipoBetoneira(Long idTipoBetoneira) {
        this.idTipoBetoneira = idTipoBetoneira;
    }

    public void setIdFabricante(Long idFabricante) {
        this.idFabricante = idFabricante;
    }
}