package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.PedidoRequestDTO;
import br.unitins.tp1.dto.PedidoResponseDTO;

public interface PedidoService {
    public PedidoResponseDTO create(PedidoRequestDTO dto);
    public PedidoResponseDTO update(Long id, PedidoRequestDTO dto);
    public void delete(Long id);
    public List<PedidoResponseDTO> findAll();
    public PedidoResponseDTO findById(Long id);
    public List<PedidoResponseDTO> getPurchaseHistoryForAuthenticatedUser();
}