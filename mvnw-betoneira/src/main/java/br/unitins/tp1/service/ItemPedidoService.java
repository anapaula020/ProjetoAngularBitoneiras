package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.PedidoDTO;
import br.unitins.tp1.dto.ItemPedidoResponseDTO;

public interface ItemPedidoService {
    public ItemPedidoResponseDTO create(PedidoDTO dto);
    public ItemPedidoResponseDTO update(Long id, PedidoDTO dto);
    public void delete(Long id);
    public List<ItemPedidoResponseDTO> findAll();
    public ItemPedidoResponseDTO findById(Long id);
}