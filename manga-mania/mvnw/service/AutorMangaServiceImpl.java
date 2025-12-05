package mssaat.org.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import mssaat.org.DTO.AutorMangaDTO;
import mssaat.org.DTO.AutorMangaResponseDTO;
import mssaat.org.model.AutorManga;
import mssaat.org.model.Sexo;
import mssaat.org.repository.AutorMangaRepository;
import java.util.stream.Collectors;

import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class AutorMangaServiceImpl implements AutorMangaService {

    @Inject
    private AutorMangaRepository autorMangaRepository;

    @Override
    @Transactional
    public AutorMangaResponseDTO create(@Valid AutorMangaDTO autorMangaDTO) {
        AutorManga autor = new AutorManga();
        autor.setNome(autorMangaDTO.nome());
        autor.setAnoNascimento(autorMangaDTO.anoNascimento());
        autor.setNacionalidade(autorMangaDTO.nacionalidade());
        autor.setSexo(Sexo.valueOf(autorMangaDTO.sexo()));
        autorMangaRepository.persist(autor);
        return AutorMangaResponseDTO.valueOf(autor);
    }

    @Override
    @Transactional
    public void update(long id, @Valid AutorMangaDTO autorMangaDTO) {
        AutorManga autor = autorMangaRepository.findById(id);
        if (autor != null) {
            autor.setNome(autorMangaDTO.nome());
            autor.setAnoNascimento(autorMangaDTO.anoNascimento());
            autor.setNacionalidade(autorMangaDTO.nacionalidade());
            autor.setSexo(Sexo.valueOf(autorMangaDTO.sexo()));
            autorMangaRepository.persist(autor);
        }
    }

    @Override
    @Transactional
    public void delete(long id) {
        autorMangaRepository.deleteById(id);
    }

    @Override
    public long count() {
        return autorMangaRepository.count();
    }

    @Override
    public AutorMangaResponseDTO findById(long id) {
        AutorManga autor = autorMangaRepository.findById(id);
        if (autor != null) {
            return AutorMangaResponseDTO.valueOf(autor);
        }
        return null;
    }

    @Override
    public List<AutorMangaResponseDTO> findByName(String name) {
        return autorMangaRepository.findByName(name).stream().map(AutorMangaResponseDTO::valueOf).toList();
    }

    @Override
    @Transactional
    public List<AutorMangaResponseDTO> findAll(int page, int pageSize) {
        Sort sort = Sort.by("dataAlteracao").descending().and("dataCadastro").ascending(); 
        
        return autorMangaRepository.findAll(sort).page(page, pageSize).list().stream().map(AutorMangaResponseDTO::valueOf)
        .collect(Collectors.toList());
    }

}