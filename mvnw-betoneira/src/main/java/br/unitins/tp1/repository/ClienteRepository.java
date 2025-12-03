// src/main/java/br/unitins/tp1/repository/ClienteRepository.java
package br.unitins.tp1.repository;

import br.unitins.tp1.model.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {

    public List<Cliente> findByNome(String nome) {
        return find("UPPER(nome) LIKE UPPER(?1)", "%" + nome + "%").list();
    }

    public Cliente findByEmail(String email) {
        return find("email", email).firstResult();
    }

    // Removed the problematic findByEmailAndSenha for Bcrypt
    // public Cliente findByEmailAndSenha(String email, String senha) {
    //     return find("email = ?1 and senha = ?2", email, senha).firstResult();
    // }
}