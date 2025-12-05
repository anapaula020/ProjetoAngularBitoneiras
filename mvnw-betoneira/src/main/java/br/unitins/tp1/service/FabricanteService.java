package mssaat.org.service;

import java.util.List;

import jakarta.validation.Valid;
import mssaat.org.DTO.AdministradorDTO;
import mssaat.org.DTO.AdministradorResponseDTO;

public interface FabricanteService {
    public FabricanteResponseDTO create(FabricanteRequestDTO dto);
    public FabricanteResponseDTO update(Long id, FabricanteRequestDTO dto);
    public void delete(Long id);
    public List<FabricanteResponseDTO> findAll();
    public FabricanteResponseDTO findById(Long id);
    public List<FabricanteResponseDTO> findByNome(String nome);
}