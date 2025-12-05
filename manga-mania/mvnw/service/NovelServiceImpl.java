package mssaat.org.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import mssaat.org.DTO.NovelDTO;
import mssaat.org.DTO.NovelResponseDTO;
import mssaat.org.model.GeneroNovel;
import mssaat.org.model.Novel;
import mssaat.org.repository.EscritorNovelRepository;
import mssaat.org.repository.NovelRepository;

@ApplicationScoped
public class NovelServiceImpl implements NovelService {
    @Inject
    private NovelRepository novelRepository;

    @Inject
    private EscritorNovelRepository escritorNovelRepository;

    @Override
    @Transactional
    public NovelResponseDTO create(@Valid NovelDTO novel) {
        Novel novelEntity = new Novel();
        novelEntity.setNome(novel.nome());
        novelEntity.setImageUrl(novel.imageUrl());
        novelEntity.setAnoPublicacao(novel.lancamento());
        novelEntity.setEscritorNovel(escritorNovelRepository.findById(novel.idAutor()));
        novelEntity.setGenero(GeneroNovel.valueOf(novel.genero()));
        novelEntity.setSinopse(novel.sinopse());
        novelEntity.setPaginas(novel.paginas());
        novelEntity.setCapitulos(novel.capitulos());
        novelEntity.setPreco(novel.preco());
        novelEntity.setEstoque(novel.estoque());
        escritorNovelRepository.findById(novel.idAutor()).getNovels().add(novelEntity);
        novelRepository.persist(novelEntity);
        return NovelResponseDTO.valueOf(novelEntity);
    }

    @Override
    @Transactional
    public void update(long id, @Valid NovelDTO novel) {
        Novel novelEntity = novelRepository.findById(id);
        novelEntity.setNome(novel.nome());
        novelEntity.setImageUrl(novel.imageUrl());
        novelEntity.setAnoPublicacao(novel.lancamento());
        novelEntity.setEscritorNovel(escritorNovelRepository.findById(novel.idAutor()));
        novelEntity.setGenero(GeneroNovel.valueOf(novel.genero()));
        novelEntity.setSinopse(novel.sinopse());
        novelEntity.setPaginas(novel.paginas());
        novelEntity.setCapitulos(novel.capitulos());
        novelEntity.setPreco(novel.preco());
        novelEntity.setEstoque(novel.estoque());
    }

    @Override
    @Transactional
    public void delete(long id) {
        novelRepository.deleteById(id);
    }

    @Override
    public long count() {
        return novelRepository.count();
    }

    @Override
    public NovelResponseDTO findById(long id) {
        Novel novel = novelRepository.findById(id);
        return NovelResponseDTO.valueOf(novel);
    }

    @Override
    public List<NovelResponseDTO> findByName(String name) {
        return novelRepository.findByName(name).stream().map(NovelResponseDTO::valueOf).toList();
    }

    @Override
    public List<NovelResponseDTO> findByEscritor(long authorId) {
        return novelRepository.findByEscritor(authorId).stream().map(NovelResponseDTO::valueOf).toList();
    }

    @Override
    public List<NovelResponseDTO> findByGenre(int genreId) {
        return novelRepository.findByGenre(GeneroNovel.valueOf(genreId)).stream().map(NovelResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<NovelResponseDTO> findAll(int page, int pageSize) {
        return novelRepository.findAll().page(page, pageSize).stream().map(NovelResponseDTO::valueOf).toList();
    }
}