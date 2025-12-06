package br.unitins.tp1.service;

import br.unitins.tp1.dto.ClienteResponseDTO;

public interface JwtService {
    public String generateJwt(ClienteResponseDTO dto);
}