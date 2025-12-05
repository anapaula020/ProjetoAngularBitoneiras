package mssaat.org.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Avaliacao extends DefaultEntity {
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "id_manga")
    private Manga manga;
    @Column(length = 512, nullable = false)
    private String comentario;
    private Integer estrelas;

    public Avaliacao() {
    }

    public Avaliacao(Usuario usuario, Manga manga, String comentario, Integer estrelas) {
        this.usuario = usuario;
        this.manga = manga;
        this.comentario = comentario;
        this.estrelas = estrelas;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Manga getManga() {
        return manga;
    }

    public void setManga(Manga manga) {
        this.manga = manga;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getEstrelas() {
        return estrelas;
    }

    public void setEstrelas(Integer estrelas) {
        this.estrelas = estrelas;
    }
}