package br.unitins.tp1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

@Entity
public class Betoneira extends DefaultEntity { // Assumindo que DefaultEntity já cuida do ID
    private String nome;
    private String descricao;
    private Double preco;
    private Integer quantidadeEstoque;
    private String modelo;
    private String marca; // Campo adicionado
    private Double capacidade; // Campo adicionado

    @Enumerated(EnumType.STRING)
    private TipoBetoneiraEnum tipo; // Campo adicionado

    @ManyToOne
    @JoinColumn(name = "fabricante_id")
    private Fabricante fabricante; // Campo adicionado

    public Betoneira() {
    }

    // Getters
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public Double getPreco() { return preco; }
    public Integer getQuantidadeEstoque() { return quantidadeEstoque; }
    public String getModelo() { return modelo; }
    public String getMarca() { return marca; }
    public Double getCapacidade() { return capacidade; }
    public TipoBetoneiraEnum getTipo() { return tipo; }
    public Fabricante getFabricante() { return fabricante; }

    // Setters
    public void setNome(String nome) { this.nome = nome; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setPreco(Double preco) { this.preco = preco; }
    public void setQuantidadeEstoque(Integer quantidadeEstoque) { this.quantidadeEstoque = quantidadeEstoque; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setMarca(String marca) { this.marca = marca; }
    public void setCapacidade(Double capacidade) { this.capacidade = capacidade; }
    public void setTipo(TipoBetoneiraEnum tipo) { this.tipo = tipo; }
    public void setFabricante(Fabricante fabricante) { this.fabricante = fabricante; }
}