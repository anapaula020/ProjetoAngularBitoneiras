package br.unitins.tp1.repository;

import br.unitins.tp1.model.Fabricante;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List; // Adicionado para List

@ApplicationScoped
public class FabricanteRepository implements PanacheRepository<Fabricante> {
    public List<Fabricante> findByNome(String nome) { // Adicionado
        return find("nome = ?1", nome).list();
    }
}