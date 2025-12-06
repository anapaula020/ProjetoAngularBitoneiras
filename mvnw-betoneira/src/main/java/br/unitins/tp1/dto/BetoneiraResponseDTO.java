package br.unitins.tp1.dto;

import br.unitins.tp1.model.EnumTipoBetoneira;

public class BetoneiraResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private Double preco;
    private Integer quantidadeEstoque;
    private String marca;
    private String modelo;
    private Double capacidade;
    private EnumTipoBetoneira tipo;
    private FabricanteResponseDTO fabricante;

    public BetoneiraResponseDTO() {
    }

    public BetoneiraResponseDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public BetoneiraResponseDTO(Long id, String nome, String descricao, Double preco, Integer quantidadeEstoque,
            String marca, String modelo, Double capacidade, EnumTipoBetoneira tipo, FabricanteResponseDTO fabricante) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.marca = marca;
        this.modelo = modelo;
        this.capacidade = capacidade;
        this.tipo = tipo;
        this.fabricante = fabricante;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Double getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Double capacidade) {
        this.capacidade = capacidade;
    }

    public EnumTipoBetoneira getTipo() {
        return tipo;
    }

    public void setTipo(EnumTipoBetoneira tipo) {
        this.tipo = tipo;
    }

    public FabricanteResponseDTO getFabricante() {
        return fabricante;
    }

    public void setFabricante(FabricanteResponseDTO fabricante) {
        this.fabricante = fabricante;
    }

}