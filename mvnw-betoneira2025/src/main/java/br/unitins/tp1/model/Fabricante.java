package br.unitins.tp1.model; // Ajuste este pacote se necessário

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
// Importações adicionais podem ser necessárias dependendo do que você mais adicionar (ex: @Column, @OneToMany)

@Entity // Marca esta classe como uma entidade JPA
public class Fabricante {

    @Id // Marca 'id' como a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura a geração automática do ID
    private Long id;
    private String nome; // Nome do fabricante
    private String cnpj; // CNPJ do fabricante, como um exemplo de campo adicional

    // Construtor padrão (sem argumentos)
    // É essencial para frameworks JPA e para a criação de instâncias via reflexão
    public Fabricante() {
    }

    // Construtor com argumentos (opcional, mas útil para criar objetos facilmente)
    public Fabricante(String nome, String cnpj) {
        this.nome = nome;
        this.cnpj = cnpj;
    }

    // --- Getters (Métodos para obter os valores dos campos) ---
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    // --- Setters (Métodos para definir os valores dos campos) ---
    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    // --- Métodos Adicionais Comuns (Opcional, mas útil) ---

    // Sobrescreve toString() para uma representação de string mais legível do objeto
    @Override
    public String toString() {
        return "Fabricante{" +
               "id=" + id +
               ", nome='" + nome + '\'' +
               ", cnpj='" + cnpj + '\'' +
               '}';
    }

    // Sobrescreve equals() e hashCode() para comparação correta de objetos
    // Essencial para coleções (Set, Map) e para garantir a identidade da entidade
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fabricante that = (Fabricante) o;
        return id != null && id.equals(that.id); // Comparação por ID para entidades persistidas
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0; // Hash baseado no ID
    }
}