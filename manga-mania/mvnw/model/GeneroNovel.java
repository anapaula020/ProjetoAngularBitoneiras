package mssaat.org.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import mssaat.org.util.Error;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GeneroNovel {
    ROMANCE(1, "Romance"),
    FANTASIA(2, "Fantasia"),
    ACAO(3, "Ação"),
    FICCAO_CIENTIFICA(4, "Ficção Científica"),
    MISTERIO(5, "Mistério"),
    DRAMA(6, "Drama"),
    COMEDIA(7, "Comédia"),
    HORROR(8, "Horror"),
    HISTORICO(9, "Histórico"),
    SLICE_OF_LIFE(10, "Slice of Life");

    private int id;
    private String nome;

    GeneroNovel(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    @SuppressWarnings("resource")
    public static GeneroNovel valueOf(Integer id) throws IllegalArgumentException {
        for (GeneroNovel genero : GeneroNovel.values()) {
            if (genero.id == id)
                return genero;
        }
        throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new Error("404", "Gênero não encontrado para o ID fornecido: " + id))
                    .build());
    }
}