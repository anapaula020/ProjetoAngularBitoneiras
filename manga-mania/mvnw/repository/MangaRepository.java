package mssaat.org.repository;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import mssaat.org.model.GeneroManga;
import mssaat.org.model.Manga;

@ApplicationScoped
public class MangaRepository implements PanacheRepository<Manga> {
    public PanacheQuery<Manga> findByName(String name) {
        return find("UPPER(nome) LIKE ?1 ORDER BY id", "%" + name.toUpperCase() + "%");
    }

    public PanacheQuery<Manga> findByAuthor(long authorId) {
        return find("autorManga.id = ?1", authorId);
    }

    public PanacheQuery<Manga> findByGenre(GeneroManga genre) {
        return find("generoManga = ?1", genre);
    }

    public List<Manga> findByImageUrl(String imageUrl) {
        return find("imageUrl LIKE ?1", imageUrl).list();
    }
    @Override
    public PanacheQuery<Manga> findAll() {
        return find("ORDER BY id");
    }
    public List<Manga> findByPrice(double price, double price2) {
        return find("preco BETWEEN ?1 AND ?2", price, price2).list();
    }
}