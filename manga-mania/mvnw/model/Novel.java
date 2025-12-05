package mssaat.org.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Novel extends Livro {
    @Column(length = 60, nullable = false)
    private GeneroNovel genero;
    @Column(nullable = false)
    private int capitulos;
    @ManyToOne
    private EscritorNovel escritorNovel;

    public EscritorNovel getEscritorNovel() {
        return escritorNovel;
    }

    public void setEscritorNovel(EscritorNovel escritorNovel) {
        this.escritorNovel = escritorNovel;
    }

    public int getCapitulos() {
        return capitulos;
    }

    public void setCapitulos(int capitulos) {
        this.capitulos = capitulos;
    }

    public GeneroNovel getGenero() {
        return genero;
    }

    public void setGenero(GeneroNovel genero) {
        this.genero = genero;
    }
}