package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.BetoneiraRequestDTO;
import br.unitins.tp1.dto.BetoneiraResponseDTO;

public interface BetoneiraService {
    public BetoneiraResponseDTO create(BetoneiraRequestDTO dto);
    public BetoneiraResponseDTO update(Long id, BetoneiraRequestDTO dto);
    public void delete(Long id);
    public List<BetoneiraResponseDTO> findAll();
    public BetoneiraResponseDTO findById(Long id);
    public List<BetoneiraResponseDTO> findByNome(String nome);
    public List<BetoneiraResponseDTO> findByTipo(String nome);
}