package mssaat.org.service;

import java.util.List;

import jakarta.validation.Valid;
import mssaat.org.DTO.AdministradorDTO;
import mssaat.org.DTO.AdministradorResponseDTO;

public interface ItemPedidoService {
    public ItemPedidoResponseDTO create(ItemPedidoRequestDTO dto);
    public ItemPedidoResponseDTO update(Long id, ItemPedidoRequestDTO dto);
    public void delete(Long id);
    public List<ItemPedidoResponseDTO> findAll();
    public ItemPedidoResponseDTO findById(Long id);
}