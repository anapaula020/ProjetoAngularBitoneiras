package mssaat.org.service;

import mssaat.org.DTO.AdministradorResponseDTO;
import mssaat.org.DTO.UsuarioResponseDTO;

public interface JwtService {
    public String generateJwt(AdministradorResponseDTO userDto);

    public String generateJwt(UsuarioResponseDTO adminDto);
}