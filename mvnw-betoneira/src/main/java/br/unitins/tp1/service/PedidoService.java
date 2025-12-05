package mssaat.org.service;

import java.util.List;

import jakarta.validation.Valid;
import mssaat.org.DTO.AdministradorDTO;
import mssaat.org.DTO.AdministradorResponseDTO;

public interface PedidoService {
    public PedidoResponseDTO create(PedidoRequestDTO dto);
    public PedidoResponseDTO update(Long id, PedidoRequestDTO dto);
    public void delete(Long id);
    public List<PedidoResponseDTO> findAll();
    public PedidoResponseDTO findById(Long id);
    public List<PedidoResponseDTO> getPurchaseHistoryForAuthenticatedUser();
}