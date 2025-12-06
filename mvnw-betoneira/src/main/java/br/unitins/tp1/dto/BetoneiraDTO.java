package br.unitins.tp1.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class BetoneiraDTO {
    @NotBlank(message = "O nome não pode ser nulo ou vazio.")
    @Length(min = 2, max = 100, message = "O nome deve ter entre 2 a 100 caracteres.")
    private String nome;
    @NotBlank(message = "A descrição não pode ser nulo ou vazio.")
    @Length(min = 2, max = 100, message = "A descrição deve ter entre 2 a 100 caracteres.")
    private String descricao;
    @Min(value = 1, message = "Preco precisa ser maior do que 0.")
    private Integer estoque;
    @NotNull(message = "O preço não pode ser nulo.")
    @Positive(message = "O preço deve ser um valor positivo.")
    private Double preco;
    @NotNull(message = "O tipo de betoneira não pode ser nulo.")
    @Length(min = 2, max = 100, message = "O tipo deve ter entre 2 a 100 caracteres.")
    private String tipo;
    @NotNull(message = "O ID do fabricante não pode ser nulo.")
    private Long idFabricante;

    public BetoneiraDTO() {
    }

    public BetoneiraDTO(String nome, String descricao, Integer estoque, Double preco, String tipo, Long idFabricante) {
        this.nome = nome;
        this.descricao = descricao;
        this.estoque = estoque;
        this.preco = preco;
        this.tipo = tipo;
        this.idFabricante = idFabricante;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getIdFabricante() {
        return idFabricante;
    }

    public void setIdFabricante(Long idFabricante) {
        this.idFabricante = idFabricante;
    }

}