package mssaat.org.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import mssaat.org.model.AutorManga;

@ApplicationScoped
public class AutorMangaRepository implements PanacheRepository<AutorManga> {
    public PanacheQuery<AutorManga> findByName(String name) {
        return find("UPPER(nome) LIKE ?1", "%" + name.toUpperCase() + "%");
    }

}