package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.EnderecoRequestDTO;
import br.unitins.tp1.dto.EnderecoResponseDTO;

public interface EnderecoService {
    public EnderecoResponseDTO create(EnderecoRequestDTO dto);
    public EnderecoResponseDTO update(Long id, EnderecoRequestDTO dto);
    public void delete(Long id);
    public List<EnderecoResponseDTO> findAll();
    public EnderecoResponseDTO findById(Long id);
    public List<EnderecoResponseDTO> findByAuthenticatedUser();
}