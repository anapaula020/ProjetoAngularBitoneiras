package mssaat.org.DTO;

import mssaat.org.model.AutorManga;
import mssaat.org.model.Sexo;

public record AutorMangaResponseDTO(Long id, String nome, int anoNascimento, String nacionalidade, Sexo sexo) {
    public static AutorMangaResponseDTO valueOf(AutorManga autor){
        return new AutorMangaResponseDTO(autor.getId(), autor.getNome(), autor.getAnoNascimento(), autor.getNacionalidade(), autor.getSexo());
    }
}
