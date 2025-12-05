package mssaat.org.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import mssaat.org.DTO.AutorMangaDTO;
import mssaat.org.DTO.AutorMangaResponseDTO;

@ApplicationScoped
public interface AutorMangaService {
    public AutorMangaResponseDTO create(@Valid AutorMangaDTO autorManga);

    public long count();

    public List<AutorMangaResponseDTO> findAll(int page, int pageSize);

    public AutorMangaResponseDTO findById(long id);

    public List<AutorMangaResponseDTO> findByName(String name);

    public void update(long id, @Valid AutorMangaDTO autorManga);

    public void delete(long id);
}