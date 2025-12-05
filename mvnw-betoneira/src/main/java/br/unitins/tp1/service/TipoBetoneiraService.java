package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.TipoBetoneiraDTO;
import br.unitins.tp1.dto.TipoBetoneiraResponseDTO;

public interface TipoBetoneiraService {
    public TipoBetoneiraResponseDTO create(TipoBetoneiraDTO dto);
    public TipoBetoneiraResponseDTO update(Long id, TipoBetoneiraDTO dto);
    public void delete(Long id);
    public List<TipoBetoneiraResponseDTO> findAll();
    public TipoBetoneiraResponseDTO findById(Long id);
    public List<TipoBetoneiraResponseDTO> findByNome(String nome);
}