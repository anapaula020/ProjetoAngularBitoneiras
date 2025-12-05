package mssaat.org.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import mssaat.org.DTO.AvaliacaoDTO;
import mssaat.org.DTO.AvaliacaoResponseDTO;
import mssaat.org.model.Avaliacao;
import mssaat.org.repository.AvaliacaoRepository;
import mssaat.org.repository.MangaRepository;
import mssaat.org.repository.UsuarioRepository;
import mssaat.org.validation.ValidationException;

@ApplicationScoped
public class AvaliacaoServiceImpl implements AvaliacaoService {
    @Inject
    public AvaliacaoRepository avaliacaoRepository;

    @Inject
    private UsuarioRepository usuarioRepository;

    @Inject
    private MangaRepository mangaRepository;

    @Override
    @Transactional
    public AvaliacaoResponseDTO create(@Valid AvaliacaoDTO avaliacaoDto) {
        Avaliacao avaliacaoBanco = new Avaliacao();
        avaliacaoBanco.setUsuario(usuarioRepository.findById(avaliacaoDto.idUsuario()));
        avaliacaoBanco.setManga(mangaRepository.findById(avaliacaoDto.idManga()));
        avaliacaoBanco.setComentario(avaliacaoDto.comentario());
        avaliacaoBanco.setEstrelas(avaliacaoDto.estrelas());
        avaliacaoRepository.persist(avaliacaoBanco);
        return AvaliacaoResponseDTO.valueOf(avaliacaoBanco);
    }

    @Override
    @Transactional
    public void update(Long id, AvaliacaoDTO avaliacaoDto) {
        Avaliacao avaliacaoBanco = avaliacaoRepository.findById(id);
        if (avaliacaoBanco == null) {
            throw new ValidationException("id", "Avaliação não existe.");
        }
        avaliacaoBanco.setUsuario(usuarioRepository.findById(avaliacaoDto.idUsuario()));
        avaliacaoBanco.setManga(mangaRepository.findById(avaliacaoDto.idManga()));
        avaliacaoBanco.setComentario(avaliacaoDto.comentario());
        avaliacaoBanco.setEstrelas(avaliacaoDto.estrelas());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        avaliacaoRepository.deleteById(id);
    }

    @Override
    public Long count() {
        return avaliacaoRepository.count();
    }

    @Override
    public AvaliacaoResponseDTO findById(Long id) {
        Avaliacao avaliacao = avaliacaoRepository.findById(id);
        if (avaliacao == null)
            return null;
        return AvaliacaoResponseDTO.valueOf(avaliacao);
    }

    @Override
    public List<AvaliacaoResponseDTO> findAll() {
        return avaliacaoRepository
                .listAll()
                .stream()
                .map(e -> AvaliacaoResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<AvaliacaoResponseDTO> findByComentario(String comentario) {
        return avaliacaoRepository
                .findByComentario(comentario)
                .stream()
                .map(e -> AvaliacaoResponseDTO.valueOf(e)).toList();
    }
}