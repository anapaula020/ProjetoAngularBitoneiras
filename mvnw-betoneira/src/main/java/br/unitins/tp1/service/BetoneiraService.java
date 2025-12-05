package mssaat.org.service;

import java.util.List;

import jakarta.validation.Valid;
import mssaat.org.DTO.AdministradorDTO;
import mssaat.org.DTO.AdministradorResponseDTO;

public interface BetoneiraService {
    public BetoneiraResponseDTO create(BetoneiraRequestDTO dto);
    public BetoneiraResponseDTO update(Long id, BetoneiraRequestDTO dto);
    public void delete(Long id);
    public List<BetoneiraResponseDTO> findAll();
    public BetoneiraResponseDTO findById(Long id);
    public List<BetoneiraResponseDTO> findByNome(String nome);
    public List<BetoneiraResponseDTO> findByTipo(String nome);
}