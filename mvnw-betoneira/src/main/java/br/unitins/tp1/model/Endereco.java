package br.unitins.tp1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Endereco extends DefaultEntity {

    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade; // Adicionado
    private String estado; // Adicionado
    private String cep;

    @ManyToOne
    @JoinColumn(name = "cliente_id") // Confirmar o nome da FK
    private Cliente cliente; // Adicionado para associação com Cliente

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() { // Adicionado
        return cidade;
    }

    public void setCidade(String cidade) { // Adicionado
        this.cidade = cidade;
    }

    public String getEstado() { // Adicionado
        return estado;
    }

    public void setEstado(String estado) { // Adicionado
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Cliente getCliente() { // Adicionado
        return cliente;
    }

    public void setCliente(Cliente cliente) { // Adicionado
        this.cliente = cliente;
    }
}