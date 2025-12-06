package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.PagamentoDTO;
import br.unitins.tp1.dto.PagamentoResponseDTO;
import br.unitins.tp1.model.EnumStatusPagamento;

public interface PagamentoService {
    // public PagamentoResponseDTO create(PagamentoDTO dto);
    public PagamentoResponseDTO update(Long id, PagamentoDTO dto);
    public void delete(Long id);
    public PagamentoResponseDTO findById(Long id);
    public List<PagamentoResponseDTO> findAll();
    public List<PagamentoResponseDTO> findByStatusPagamento(String status);
    public void processarPagamento(Long idPagamento, EnumStatusPagamento novoStatus);
    public List<PagamentoResponseDTO> findByAuthenticatedUser();
    public List<PagamentoResponseDTO> findByNome(String nome);
}