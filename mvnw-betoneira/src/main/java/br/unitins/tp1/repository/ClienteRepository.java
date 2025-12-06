package br.unitins.tp1.repository;

import br.unitins.tp1.model.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {
    public Cliente findNomeEqual(String username) {
        return find("username = ?1", username).firstResult();
    }

    public PanacheQuery<Cliente> findByUsername(String username) {
        return find("username LIKE ?1", "%" + username + "%");
    }

    public PanacheQuery<Cliente> findByEmail(String email) {
        return find("email LIKE ?1", "%" + email + "%");
    }

    public PanacheQuery<Cliente> findByCpf(String cpf) {
        return find("cpf LIKE ?1", "%" + cpf + "%");
    }

    public PanacheQuery<Cliente> findByEndereco(String endereco) {
        return find("endereco LIKE ?1", "%" + endereco + "%");
    }

    public Cliente findByUsernameAndSenha(String username, String senha) {
        return find("username = ?1 AND senha = ?2", username, senha).firstResult();
    }
}