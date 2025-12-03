package br.unitins.tp1.dto;

import br.unitins.tp1.model.Betoneira;
import br.unitins.tp1.model.TipoBetoneiraEnum; // Make sure this is imported

public class BetoneiraResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private Double preco;
    private Integer quantidadeEstoque;
    private String marca;
    private String modelo;
    private Double capacidade;
    private TipoBetoneiraEnum tipo; // TipoBetoneiraEnum está no Model
    private FabricanteResponseDTO fabricante;

    public BetoneiraResponseDTO() {
    }

    // Construtor para mapear a partir da entidade Betoneira
    public BetoneiraResponseDTO(Betoneira betoneira) {
        this.id = betoneira.getId();
        this.nome = betoneira.getNome();
        this.descricao = betoneira.getDescricao();
        this.preco = betoneira.getPreco();
        this.quantidadeEstoque = betoneira.getQuantidadeEstoque();
        this.marca = betoneira.getMarca();
        this.modelo = betoneira.getModelo();
        this.capacidade = betoneira.getCapacidade();
        this.tipo = betoneira.getTipo(); // Usando getTipo() que retorna TipoBetoneiraEnum
        this.fabricante = betoneira.getFabricante() != null ? FabricanteResponseDTO.valueOf(betoneira.getFabricante()) : null;
    }

    public static BetoneiraResponseDTO valueOf(Betoneira betoneira) {
        return new BetoneiraResponseDTO(betoneira);
    }

    // Getters
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public Double getPreco() { return preco; }
    public Integer getQuantidadeEstoque() { return quantidadeEstoque; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public Double getCapacidade() { return capacidade; }
    public TipoBetoneiraEnum getTipo() { return tipo; }
    public FabricanteResponseDTO getFabricante() { return fabricante; }

    // Setters (para DTOs de resposta, setters são geralmente omitidos, mas se o framework precisar, mantenha)
    public void setId(Long id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setPreco(Double preco) { this.preco = preco; }
    public void setQuantidadeEstoque(Integer quantidadeEstoque) { this.quantidadeEstoque = quantidadeEstoque; }
    public void setMarca(String marca) { this.marca = marca; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setCapacidade(Double capacidade) { this.capacidade = capacidade; }
    public void setTipo(TipoBetoneiraEnum tipo) { this.tipo = tipo; }
    public void setFabricante(FabricanteResponseDTO fabricante) { this.fabricante = fabricante; }
}