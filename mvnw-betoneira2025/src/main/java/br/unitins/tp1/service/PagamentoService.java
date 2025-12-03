// src/main/java/br/unitins/tp1/service/PagamentoService.java
package br.unitins.tp1.service;

import br.unitins.tp1.dto.PagamentoRequestDTO;
import br.unitins.tp1.dto.PagamentoResponseDTO;
import br.unitins.tp1.model.StatusPagamento;

import java.util.List;

public interface PagamentoService {

    PagamentoResponseDTO create(PagamentoRequestDTO dto);
    PagamentoResponseDTO update(Long id, PagamentoRequestDTO dto);
    void delete(Long id);
    PagamentoResponseDTO findById(Long id);
    List<PagamentoResponseDTO> findAll();
    List<PagamentoResponseDTO> findByStatusPagamento(String status);
    void processarPagamento(Long idPagamento, StatusPagamento novoStatus);
    List<PagamentoResponseDTO> findByAuthenticatedUser();
    List<PagamentoResponseDTO> findByNome(String nome); // NOVO MÉTODO
}