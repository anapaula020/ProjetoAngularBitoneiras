package mssaat.org.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import mssaat.org.DTO.EscritorNovelDTO;
import mssaat.org.DTO.EscritorNovelResponseDTO;
import mssaat.org.model.EscritorNovel;
import mssaat.org.model.Sexo;
import mssaat.org.repository.EscritorNovelRepository;

@ApplicationScoped
public class EscritorNovelServiceImpl implements EscritorNovelService {
    @Inject
    private EscritorNovelRepository escritorNovelRepository;

    @Override
    @Transactional
    public EscritorNovelResponseDTO create( @Valid EscritorNovelDTO escritorNovel) {
        EscritorNovel escNovel = new EscritorNovel();
        escNovel.setNome(escritorNovel.nome());
        escNovel.setAnoNascimento(escritorNovel.anoNascimento());
        escNovel.setNacionalidade(escritorNovel.nacionalidade());
        escNovel.setSexo(Sexo.valueOf(escritorNovel.sexo()));
        escritorNovelRepository.persist(escNovel);
        return EscritorNovelResponseDTO.valueOf(escNovel);
    }

    @Override
    @Transactional
    public void update(long id,@Valid EscritorNovelDTO escritorNovel) {
        EscritorNovel escNovel = escritorNovelRepository.findById(id);
        escNovel.setNome(escritorNovel.nome());
        escNovel.setAnoNascimento(escritorNovel.anoNascimento());
        escNovel.setNacionalidade(escritorNovel.nacionalidade());
        escNovel.setSexo(Sexo.valueOf(escritorNovel.sexo()));
    }

    @Override
    @Transactional
    public void delete(long id) {
        escritorNovelRepository.deleteById(id);
    }

    @Override
    public long count() {
        return escritorNovelRepository.count();
    }

    @Override
    public EscritorNovelResponseDTO findById(long id) {
        EscritorNovel escNovel = escritorNovelRepository.findById(id);
        return EscritorNovelResponseDTO.valueOf(escNovel);
    }

    @Override
    public List<EscritorNovelResponseDTO> findByName(String name) {
        return escritorNovelRepository.findByName(name).stream().map(EscritorNovelResponseDTO::valueOf).toList();
    }

    @Override
    public List<EscritorNovelResponseDTO> findAll(int page, int pageSize) {
        return escritorNovelRepository.findAll().page(page, pageSize).stream().map(EscritorNovelResponseDTO::valueOf).toList();
    }
}