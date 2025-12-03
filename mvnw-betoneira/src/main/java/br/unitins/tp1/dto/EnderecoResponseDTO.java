package br.unitins.tp1.dto;

import br.unitins.tp1.model.Endereco;
// import br.unitins.tp1.model.Regiao; // Removido, pois o Endereco.java não tem campo Regiao diretamente

public class EnderecoResponseDTO { // Mudado de record para class
    private Long id;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade; // Mapeia para Endereco.getCidade()
    private String estado; // Mapeia para Endereco.getEstado()
    private String cep;
    // private String regiaoNome; // Se você tivesse uma Regiao no Endereco Model

    // Construtor padrão
    public EnderecoResponseDTO() {
    }

    // Construtor para mapear a partir da entidade Endereco
    public EnderecoResponseDTO(Endereco endereco) {
        this.id = endereco.getId();
        this.logradouro = endereco.getLogradouro();
        this.numero = endereco.getNumero();
        this.complemento = endereco.getComplemento();
        this.bairro = endereco.getBairro();
        this.cidade = endereco.getCidade(); // Usando getCidade()
        this.estado = endereco.getEstado(); // Usando getEstado()
        this.cep = endereco.getCep();
        // if (endereco.getRegiao() != null) { // Exemplo se houvesse Regiao
        //     this.regiaoNome = endereco.getRegiao().name();
        // }
    }

    public static EnderecoResponseDTO valueOf(Endereco endereco) {
        return new EnderecoResponseDTO(endereco);
    }

    // Getters (sem setters para DTOs de resposta)
    public Long getId() { return id; }
    public String getLogradouro() { return logradouro; }
    public String getNumero() { return numero; }
    public String getComplemento() { return complemento; }
    public String getBairro() { return bairro; }
    public String getCidade() { return cidade; }
    public String getEstado() { return estado; }
    public String getCep() { return cep; }
    // public String getRegiaoNome() { return regiaoNome; }

    // Setters (se usados)
    public void setId(Long id) { this.id = id; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }
    public void setNumero(String numero) { this.numero = numero; }
    public void setComplemento(String complemento) { this.complemento = complemento; }
    public void setBairro(String bairro) { this.bairro = bairro; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setCep(String cep) { this.cep = cep; }
}