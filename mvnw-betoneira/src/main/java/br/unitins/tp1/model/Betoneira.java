package br.unitins.tp1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

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
    @Enumerated(EnumType.STRING)
    private EnumTipoBetoneira tipo;
    @ManyToOne
    @JoinColumn(name = "fabricante_id")
    private Fabricante fabricante;

    public Betoneira() {
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

    public String getModelo() {
        return modelo;
    }

    public String getMarca() {
        return marca;
    }

    public Double getCapacidade() {
        return capacidade;
    }

    public EnumTipoBetoneira getTipo() {
        return tipo;
    }

    public Fabricante getFabricante() {
        return fabricante;
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

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setCapacidade(Double capacidade) {
        this.capacidade = capacidade;
    }

    public void setTipo(EnumTipoBetoneira tipo) {
        this.tipo = tipo;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }

    public String getNomeImagem() {
        return nomeImagem;
    }

    public void setNomeImagem(String nomeImagem) {
        this.nomeImagem = nomeImagem;
    }
}