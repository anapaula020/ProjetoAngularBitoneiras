package br.unitins.tp1.repository;

import br.unitins.tp1.model.Betoneira;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List; // Adicionado para List

@ApplicationScoped
public class BetoneiraRepository implements PanacheRepository<Betoneira> {
    public List<Betoneira> findByNome(String nome) { // Adicionado
        return find("nome = ?1", nome).list();
    }
}