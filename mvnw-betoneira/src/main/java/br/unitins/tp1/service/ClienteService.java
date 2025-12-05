package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.ClienteRequestDTO;
import br.unitins.tp1.dto.ClienteResponseDTO;

public interface ClienteService {
    public ClienteResponseDTO create(ClienteRequestDTO dto);
    public ClienteResponseDTO update(Long id, ClienteRequestDTO dto);
    public void delete(Long id);
    public List<ClienteResponseDTO> findAll();
    public ClienteResponseDTO findById(Long id);
    public List<ClienteResponseDTO> findByNome(String nome);
    public ClienteResponseDTO findByEmailAndSenha(String email, String rawSenha);
}