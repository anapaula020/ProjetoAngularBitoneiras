package br.unitins.tp1.repository;

import br.unitins.tp1.model.Betoneira;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List; 

@ApplicationScoped
public class BetoneiraRepository implements PanacheRepository<Betoneira> {
    public List<Betoneira> findByNome(String nome) { 
        return find("nome = ?1", nome).list();
    }
    
    public List<Betoneira> findByTipo(String nome) { 
        return find("tipo = ?1", nome).list();
    }
}