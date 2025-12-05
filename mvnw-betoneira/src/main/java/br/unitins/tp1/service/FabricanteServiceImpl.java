package br.unitins.tp1.service;

import br.unitins.tp1.dto.FabricanteRequestDTO;
import br.unitins.tp1.dto.FabricanteResponseDTO;
import br.unitins.tp1.exception.ServiceException;
import br.unitins.tp1.model.Fabricante;
import br.unitins.tp1.repository.FabricanteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class BetoneiraServiceImpl implements BetoneiraService {
    @Inject
    FabricanteRepository fabricanteRepository;

    @Inject
    SecurityContext securityContext;

    @Transactional
    public FabricanteResponseDTO create(FabricanteRequestDTO dto) {
        if (!securityContext.isUserInRole("ADMIN")) {
            throw new ServiceException("Usuário não autorizado a criar fabricantes.", Response.Status.FORBIDDEN);
        }
        Fabricante fabricante = new Fabricante();
        fabricante.setNome(dto.getNome()); 
        fabricanteRepository.persist(fabricante);
        return FabricanteResponseDTO.valueOf(fabricante);
    }

    @Transactional
    public FabricanteResponseDTO update(Long id, FabricanteRequestDTO dto) {
        if (!securityContext.isUserInRole("ADMIN")) {
            throw new ServiceException("Usuário não autorizado a atualizar fabricantes.", Response.Status.FORBIDDEN);
        }
        Fabricante fabricante = fabricanteRepository.findById(id);
        if (fabricante == null) {
            throw new ServiceException("Fabricante não encontrado.", Response.Status.NOT_FOUND);
        }
        fabricante.setNome(dto.getNome()); 
        fabricanteRepository.persist(fabricante);
        return FabricanteResponseDTO.valueOf(fabricante);
    }

    @Transactional
    public void delete(Long id) {
        if (!securityContext.isUserInRole("ADMIN")) {
            throw new ServiceException("Usuário não autorizado a deletar fabricantes.", Response.Status.FORBIDDEN);
        }
        Fabricante fabricante = fabricanteRepository.findById(id);
        if (fabricante == null) {
            throw new ServiceException("Fabricante não encontrado.", Response.Status.NOT_FOUND);
        }
        fabricanteRepository.delete(fabricante);
    }

    public List<FabricanteResponseDTO> findAll() {
        return fabricanteRepository.listAll().stream()
                .map(FabricanteResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    public FabricanteResponseDTO findById(Long id) {
        Fabricante fabricante = fabricanteRepository.findById(id);
        if (fabricante == null) {
            throw new ServiceException("Fabricante não encontrado.", Response.Status.NOT_FOUND);
        }
        return FabricanteResponseDTO.valueOf(fabricante);
    }

    public List<FabricanteResponseDTO> findByNome(String nome) {
        return fabricanteRepository.findByNome(nome).stream()
                .map(FabricanteResponseDTO::valueOf)
                .collect(Collectors.toList());
    }
}