package mssaat.org.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Manga extends Livro {
    @Column
    private String color;
    @ManyToOne
    private AutorManga autorManga;
    @Column(length = 60, nullable = false)
    private GeneroManga generoManga;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public GeneroManga getGeneroManga() {
        return generoManga;
    }

    public void setGeneroManga(GeneroManga generoManga) {
        this.generoManga = generoManga;
    }

    public AutorManga getAutor() {
        return autorManga;
    }

    public void setAutor(AutorManga autorManga) {
        this.autorManga = autorManga;
    }
}