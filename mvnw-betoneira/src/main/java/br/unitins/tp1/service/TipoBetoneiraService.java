package mssaat.org.service;

import java.util.List;

import jakarta.validation.Valid;
import mssaat.org.DTO.AdministradorDTO;
import mssaat.org.DTO.AdministradorResponseDTO;

public interface TipoBetoneiraService {
    public TipoBetoneiraResponseDTO create(TipoBetoneiraDTO dto);
    public TipoBetoneiraResponseDTO update(Long id, TipoBetoneiraDTO dto);
    public void delete(Long id);
    public List<TipoBetoneiraResponseDTO> findAll();
    public TipoBetoneiraResponseDTO findById(Long id);
    public List<TipoBetoneiraResponseDTO> findByNome(String nome);
}