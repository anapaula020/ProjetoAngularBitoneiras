package mssaat.org.service;

import java.util.List;

import jakarta.validation.Valid;

public interface AdministradorService {
    public AdministradorResponseDTO create(@Valid AdministradorDTO adminDto);
    public void update(Long id, AdministradorDTO adminDto);
    public void deleteById(Long id);
    public long count();
    public List<AdministradorResponseDTO> findAll(int page, int pageSize);
    public AdministradorResponseDTO findById( Long id);
    public List<AdministradorResponseDTO> findByUsername(String username, int page, int pageSize);
    public List<AdministradorResponseDTO> findByEmail(String email, int page, int pageSize);
    public List<AdministradorResponseDTO> findByCpf(@PathParam("cpf") String cpf, int page, int pageSize);
    public AdministradorResponseDTO login(String username, String senha);
}