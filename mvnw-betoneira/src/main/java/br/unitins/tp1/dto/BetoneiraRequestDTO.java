package br.unitins.tp1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class BetoneiraRequestDTO {

    @NotBlank(message = "O nome da betoneira não pode ser nulo ou vazio.")
    private String nome;

    private String descricao;

    @NotNull(message = "O preço da betoneira não pode ser nulo.")
    @Positive(message = "O preço da betoneira deve ser um valor positivo.")
    private Double preco;

    @NotNull(message = "A quantidade em estoque da betoneira não pode ser nula.")
    @Positive(message = "A quantidade em estoque da betoneira deve ser um valor positivo.")
    private Integer quantidadeEstoque;

    @NotNull(message = "O ID do tipo de betoneira é obrigatório.")
    private Long idTipoBetoneira;

    @NotNull(message = "O ID do fabricante é obrigatório.")
    private Long idFabricante;

    public BetoneiraRequestDTO() {
    }

    public BetoneiraRequestDTO(String nome, String descricao, Double preco, Integer quantidadeEstoque, Long idTipoBetoneira, Long idFabricante) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.idTipoBetoneira = idTipoBetoneira;
        this.idFabricante = idFabricante;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public Long getIdTipoBetoneira() {
        return idTipoBetoneira;
    }

    public Long getIdFabricante() {
        return idFabricante;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public void setIdTipoBetoneira(Long idTipoBetoneira) {
        this.idTipoBetoneira = idTipoBetoneira;
    }

    public void setIdFabricante(Long idFabricante) {
        this.idFabricante = idFabricante;
    }
}