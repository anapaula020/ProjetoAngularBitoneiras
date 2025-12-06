package br.unitins.tp1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Betoneira extends DefaultEntity {
    @Column(nullable = true, name = "nome")
    private String nome;
    @Column(nullable = true, name = "descricao")
    private String descricao;
    @Column(nullable = true, name = "preco")
    private Double preco;
    @Column(nullable = true, name = "quantidade_estoque")
    private Integer quantidadeEstoque;
    @Column(nullable = true, name = "modelo")
    private String modelo;
    @Column(nullable = true, name = "marca")
    private String marca;
    @Column(nullable = true, name = "capacidade")
    private Double capacidade;
    @Column(nullable = true, name = "nome_imagem")
    private String nomeImagem;
    private String tipo;
    @ManyToOne
    @JoinColumn(name = "fabricante_id")
    private Fabricante fabricante;

    public Betoneira() {
    }

    public Betoneira(String nome, String descricao, Double preco, Integer quantidadeEstoque, String modelo,
            String marca, Double capacidade, String nomeImagem, String tipo, Fabricante fabricante) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.modelo = modelo;
        this.marca = marca;
        this.capacidade = capacidade;
        this.nomeImagem = nomeImagem;
        this.tipo = tipo;
        this.fabricante = fabricante;
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

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Double getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Double capacidade) {
        this.capacidade = capacidade;
    }

    public String getNomeImagem() {
        return nomeImagem;
    }

    public void setNomeImagem(String nomeImagem) {
        this.nomeImagem = nomeImagem;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }

}