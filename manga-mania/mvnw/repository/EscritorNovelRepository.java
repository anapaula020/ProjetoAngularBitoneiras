package mssaat.org.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import mssaat.org.model.EscritorNovel;

@ApplicationScoped
public class EscritorNovelRepository implements PanacheRepository<EscritorNovel> {
    public PanacheQuery<EscritorNovel> findByName(String name) {
        return find("UPPER(nome) LIKE ?1", "%" + name.toUpperCase() + "%");
    }

}