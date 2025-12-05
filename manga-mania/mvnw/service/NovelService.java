package mssaat.org.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import mssaat.org.DTO.NovelDTO;
import mssaat.org.DTO.NovelResponseDTO;

@ApplicationScoped
public interface NovelService {
    public NovelResponseDTO create(@Valid NovelDTO novel);
    public NovelResponseDTO findById(long id);
    public long count();
    public List<NovelResponseDTO> findAll(int page, int pageSize);
    public List<NovelResponseDTO> findByName(String name);
    public List<NovelResponseDTO> findByEscritor(long authorId);
    public List<NovelResponseDTO> findByGenre(int genreId);
    public void update(long id, @Valid NovelDTO novel);
    public void delete(long id);
}