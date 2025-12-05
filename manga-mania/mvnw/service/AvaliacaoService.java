package mssaat.org.service;

import java.util.List;

import mssaat.org.DTO.AvaliacaoDTO;
import mssaat.org.DTO.AvaliacaoResponseDTO;
import jakarta.validation.Valid;

public interface AvaliacaoService {
    public AvaliacaoResponseDTO create(@Valid AvaliacaoDTO avaliacaoDto);
    public void update(Long id, AvaliacaoDTO dto);
    public void deleteById(Long id);
    public Long count();
    public AvaliacaoResponseDTO findById(Long id);
    public List<AvaliacaoResponseDTO> findAll();
    public List<AvaliacaoResponseDTO> findByComentario(String comentario);
}