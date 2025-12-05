package mssaat.org.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.PathParam;
import mssaat.org.DTO.AdministradorDTO;
import mssaat.org.DTO.AdministradorResponseDTO;
import mssaat.org.model.Administrador;
import mssaat.org.repository.AdministradorRepository;
import mssaat.org.validation.ValidationException;

@ApplicationScoped
public class AdministradorServiceImpl implements AdministradorService {
    @Inject
    public AdministradorRepository administradorRepository;
    @Inject
    public HashService hashService;

    @Override
    @Transactional
    public AdministradorResponseDTO create(@Valid AdministradorDTO adminDto) {
        Administrador userBanco = new Administrador();
        userBanco.setUsername(adminDto.username());
        userBanco.setEmail(adminDto.email());
        userBanco.setSenha(hashService.getHashSenha(adminDto.senha()));
        userBanco.setCpf(adminDto.cpf());
        administradorRepository.persist(userBanco);
        return AdministradorResponseDTO.valueOf(userBanco);
    }

    @Override
    @Transactional
    public void update(Long id, AdministradorDTO adminDto) {
        Administrador userBanco = administradorRepository.findById(id);
        if (userBanco == null) {
            throw new ValidationException("id", "Administrador não existe.");
        }
        userBanco.setUsername(adminDto.username());
        userBanco.setEmail(adminDto.email());
        userBanco.setSenha(hashService.getHashSenha(adminDto.senha()));
        userBanco.setCpf(adminDto.cpf());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Administrador userBanco = administradorRepository.findById(id);
        if (userBanco == null) {
            throw new ValidationException("id", "Administrador não existe.");
        }
        administradorRepository.deleteById(id);
    }

    @Override
    public long count() {
        return administradorRepository.count();
    }

    @Override
    public List<AdministradorResponseDTO> findAll(int page, int pageSize) {
        List<Administrador> admins = administradorRepository.findAll().page(page, pageSize).list();
        return admins.stream().map(e -> AdministradorResponseDTO.valueOf(e)).toList(); 
    }

    @Override
    public AdministradorResponseDTO findById( Long id) {
        Administrador admin = administradorRepository.findById(id);
        if (admin == null)
            return null;
        return AdministradorResponseDTO.valueOf(admin);
    }

    @Override
    public List<AdministradorResponseDTO> findByUsername(String username, int page, int pageSize) {
        List<Administrador> admins = administradorRepository.findByUsername(username).page(page, pageSize).list();
        return admins.stream().map(e -> AdministradorResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<AdministradorResponseDTO> findByEmail(String email, int page, int pageSize) {
        List<Administrador> admins = administradorRepository.findByEmail(email).page(page, pageSize).list();
        return admins.stream().map(e -> AdministradorResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<AdministradorResponseDTO> findByCpf(@PathParam("cpf") String cpf, int page, int pageSize) {
        List<Administrador> admins = administradorRepository.findByCpf(cpf).page(page, pageSize).list();
        return admins.stream().map(e -> AdministradorResponseDTO.valueOf(e)).toList();
    }

    @Override
    public AdministradorResponseDTO login(String username, String senha) {
        Administrador administrador = administradorRepository.findByUsernameAndSenha(username, senha);
        return AdministradorResponseDTO.valueOf(administrador);
    }
}