package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.BetoneiraDTO;
import br.unitins.tp1.dto.BetoneiraResponseDTO;

public interface BetoneiraService {
    public BetoneiraResponseDTO create(BetoneiraDTO dto);
    public BetoneiraResponseDTO update(Long id, BetoneiraDTO dto);
    public void delete(Long id);
    public List<BetoneiraResponseDTO> findAll();
    public BetoneiraResponseDTO findById(Long id);
    public List<BetoneiraResponseDTO> findByNome(String nome);
    public List<BetoneiraResponseDTO> findByTipo(String nome);
}