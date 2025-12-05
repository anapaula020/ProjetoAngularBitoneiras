package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.PagamentoRequestDTO;
import br.unitins.tp1.dto.PagamentoResponseDTO;
import br.unitins.tp1.model.StatusPagamento;

public interface PagamentoService {
    public PagamentoResponseDTO create(PagamentoRequestDTO dto);
    public PagamentoResponseDTO update(Long id, PagamentoRequestDTO dto);
    public void delete(Long id);
    public PagamentoResponseDTO findById(Long id);
    public List<PagamentoResponseDTO> findAll();
    public List<PagamentoResponseDTO> findByStatusPagamento(String status);
    public void processarPagamento(Long idPagamento, StatusPagamento novoStatus);
    public List<PagamentoResponseDTO> findByAuthenticatedUser();
    public List<PagamentoResponseDTO> findByNome(String nome);
}