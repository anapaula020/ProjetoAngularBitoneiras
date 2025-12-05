package mssaat.org.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import mssaat.org.DTO.EscritorNovelDTO;
import mssaat.org.DTO.EscritorNovelResponseDTO;

@ApplicationScoped
public interface EscritorNovelService {
    public EscritorNovelResponseDTO create(@Valid EscritorNovelDTO escritorNovel);
    public long count();
    public EscritorNovelResponseDTO findById(long id);
    public List<EscritorNovelResponseDTO> findAll(int page, int pageSize);
    public List<EscritorNovelResponseDTO> findByName(String name);
    public void update(long id, @Valid EscritorNovelDTO escritorNovel);
    public void delete(long id);
}
