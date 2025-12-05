package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.FabricanteRequestDTO;
import br.unitins.tp1.dto.FabricanteResponseDTO;

public interface FabricanteService {
    public FabricanteResponseDTO create(FabricanteRequestDTO dto);
    public FabricanteResponseDTO update(Long id, FabricanteRequestDTO dto);
    public void delete(Long id);
    public List<FabricanteResponseDTO> findAll();
    public FabricanteResponseDTO findById(Long id);
    public List<FabricanteResponseDTO> findByNome(String nome);
}