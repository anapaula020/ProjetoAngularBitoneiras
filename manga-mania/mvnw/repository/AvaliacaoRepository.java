package mssaat.org.repository;

import java.util.List;

import mssaat.org.model.Avaliacao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AvaliacaoRepository implements PanacheRepository<Avaliacao> {
    public List<Avaliacao> findByManga(Long mangaId) {
        return find("manga.id = ?1", mangaId).list();
    }

    public List<Avaliacao> findByComentario(String comentario) {
        return find("comentario LIKE ?1", "%" + comentario + "%").list();
    }
}