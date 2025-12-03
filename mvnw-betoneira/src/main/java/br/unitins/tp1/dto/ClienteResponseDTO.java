// src/main/java/br/unitins/tp1/dto/ClienteResponseDTO.java
package br.unitins.tp1.dto;

import br.unitins.tp1.model.Cliente;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClienteResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private Set<String> roles; // Retornar as roles como um Set
    private List<EnderecoResponseDTO> enderecos; // Assumindo que você quer retornar os endereços

    // Construtor padrão
    public ClienteResponseDTO() {
    }

    // Construtor completo
    public ClienteResponseDTO(Long id, String nome, String email, String cpf, Set<String> roles, List<EnderecoResponseDTO> enderecos) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.roles = roles;
        this.enderecos = enderecos;
    }

    // Método estático para conversão de Model para DTO
    public static ClienteResponseDTO valueOf(Cliente cliente) {
        List<EnderecoResponseDTO> enderecosDTO = null;
        if (cliente.getEnderecos() != null) {
            enderecosDTO = cliente.getEnderecos().stream()
                .map(EnderecoResponseDTO::valueOf)
                .collect(Collectors.toList());
        }

        return new ClienteResponseDTO(
            cliente.getId(),
            cliente.getNome(),
            cliente.getEmail(),
            cliente.getCpf(),
            cliente.getRolesAsSet(), // Usar o método que retorna Set<String>
            enderecosDTO
        );
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public List<EnderecoResponseDTO> getEnderecos() {
        return enderecos;
    }

    // Setters (geralmente não são necessários para DTOs de resposta, mas incluídos para completude)
    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public void setEnderecos(List<EnderecoResponseDTO> enderecos) {
        this.enderecos = enderecos;
    }
}