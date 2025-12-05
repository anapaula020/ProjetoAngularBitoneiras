package mssaat.org.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import mssaat.org.model.Usuario;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {
    public Usuario findNomeEqual(String username) {
        return find("username = ?1", username).firstResult();
    }

    public PanacheQuery<Usuario> findByUsername(String username) {
        return find("username LIKE ?1", "%" + username + "%");
    }

    public PanacheQuery<Usuario> findByEmail(String email) {
        return find("email LIKE ?1", "%" + email + "%");
    }

    public PanacheQuery<Usuario> findByCpf(String cpf) {
        return find("cpf LIKE ?1", "%" + cpf + "%");
    }

    public PanacheQuery<Usuario> findByEndereco(String endereco) {
        return find("endereco LIKE ?1", "%" + endereco + "%");
    }

    public Usuario findByUsernameAndSenha(String username, String senha) {
        return find("username = ?1 AND senha = ?2", username, senha).firstResult();
    }
}