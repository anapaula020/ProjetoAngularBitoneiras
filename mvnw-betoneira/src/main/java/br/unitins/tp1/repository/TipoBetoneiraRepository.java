package br.unitins.tp1.repository;

import br.unitins.tp1.model.TipoBetoneiraEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class TipoBetoneiraRepository implements PanacheRepository<TipoBetoneiraEntity> {
    public List<TipoBetoneiraEntity> findByNome(String nome) {
        return find("UPPER(nome) LIKE UPPER(?1)", "%" + nome + "%").list();
    }
}