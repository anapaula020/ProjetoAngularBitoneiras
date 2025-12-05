package mssaat.org.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import mssaat.org.util.Error;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GeneroManga {
    SHOUNEN(1, "Shounen"),
    SHOUJO(2, "Shoujo"),
    SEINEN(3, "Seinen"),
    JOSEI(4, "Josei"),
    ROMANCE(5, "Romance"),
    FANTASIA(6, "Fantasia"),
    ACAO_AVENTURA(7, "Ação/Aventura"),
    COMEDIA(8, "Comédia"),
    HORROR(9, "Horror"),
    SOBRENATURAL(10, "Sobrenatural"),
    ISEKAI(11, "Isekai"),
    MISTERIO(12, "Mistério"),
    MECHA(13, "Mecha"),
    ESPORTE(14, "Esporte"),
    PSICOLOGICO(15, "Psicológico"),
    KODOMUKE(16,"Kodomomuke ");

    private int id;
    private String nome;

    GeneroManga(int id, String nome) {
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
    public static GeneroManga value(Integer id) throws IllegalArgumentException {
        for (GeneroManga genero : GeneroManga.values()) {
            if (genero.id == id)
                return genero;
        }
        throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new Error("404", "Gênero de mangá não encontrado para o ID fornecido: " + id))
                    .build());
    }
    public static GeneroManga value(String nome) throws IllegalArgumentException {
        for (GeneroManga genero : GeneroManga.values()) {
            if (genero.nome.equals(nome))
                return genero;
        }
        throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new Error("404", "Gênero de mangá não encontrado para o nome fornecido: " + nome))
                    .build());
    }
}