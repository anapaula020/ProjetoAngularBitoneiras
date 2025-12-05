package br.unitins.tp1.service;

import br.unitins.tp1.dto.BetoneiraRequestDTO;
import br.unitins.tp1.dto.BetoneiraResponseDTO;
import br.unitins.tp1.exception.ServiceException;
import br.unitins.tp1.model.Betoneira;
import br.unitins.tp1.model.Fabricante;
import br.unitins.tp1.repository.BetoneiraRepository;
import br.unitins.tp1.repository.FabricanteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class BetoneiraService {

    @Inject
    BetoneiraRepository betoneiraRepository;

    @Inject
    FabricanteRepository fabricanteRepository;

    @Inject
    SecurityContext securityContext;

    @Transactional
    public BetoneiraResponseDTO create(BetoneiraRequestDTO dto) {
        if (!securityContext.isUserInRole("ADMIN")) {
            throw new ServiceException("Usuário não autorizado a criar betoneiras.", Response.Status.FORBIDDEN);
        }

        Betoneira betoneira = new Betoneira();
        betoneira.setNome(dto.getNome());
        betoneira.setDescricao(dto.getDescricao());
        betoneira.setPreco(dto.getPreco());
        betoneira.setQuantidadeEstoque(dto.getQuantidadeEstoque());

        Fabricante fabricante = fabricanteRepository.findById(dto.getIdFabricante());
        if (fabricante == null) {
            throw new ServiceException("Fabricante não encontrado.", Response.Status.NOT_FOUND);
        }
        betoneira.setFabricante(fabricante);

        betoneiraRepository.persist(betoneira);
        return BetoneiraResponseDTO.valueOf(betoneira);
    }

    @Transactional
    public BetoneiraResponseDTO update(Long id, BetoneiraRequestDTO dto) {
        if (!securityContext.isUserInRole("ADMIN")) {
            throw new ServiceException("Usuário não autorizado a atualizar betoneiras.", Response.Status.FORBIDDEN);
        }

        Betoneira betoneira = betoneiraRepository.findById(id);
        if (betoneira == null) {
            throw new ServiceException("Betoneira não encontrada.", Response.Status.NOT_FOUND);
        }

        betoneira.setNome(dto.getNome());
        betoneira.setDescricao(dto.getDescricao());
        betoneira.setPreco(dto.getPreco());
        betoneira.setQuantidadeEstoque(dto.getQuantidadeEstoque());

        Fabricante fabricante = fabricanteRepository.findById(dto.getIdFabricante());
        if (fabricante == null) {
            throw new ServiceException("Fabricante não encontrado.", Response.Status.NOT_FOUND);
        }
        betoneira.setFabricante(fabricante);

        betoneiraRepository.persist(betoneira);
        return BetoneiraResponseDTO.valueOf(betoneira);
    }

    @Transactional
    public void delete(Long id) {
        if (!securityContext.isUserInRole("ADMIN")) {
            throw new ServiceException("Usuário não autorizado a deletar betoneiras.", Response.Status.FORBIDDEN);
        }
        Betoneira betoneira = betoneiraRepository.findById(id);
        if (betoneira == null) {
            throw new ServiceException("Betoneira não encontrada.", Response.Status.NOT_FOUND);
        }
        betoneiraRepository.delete(betoneira);
    }

    public List<BetoneiraResponseDTO> findAll() {
        return betoneiraRepository.listAll().stream()
                .map(BetoneiraResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    public BetoneiraResponseDTO findById(Long id) {
        Betoneira betoneira = betoneiraRepository.findById(id);
        if (betoneira == null) {
            throw new ServiceException("Betoneira não encontrada.", Response.Status.NOT_FOUND);
        }
        return BetoneiraResponseDTO.valueOf(betoneira);
    }

    public List<BetoneiraResponseDTO> findByNome(String nome) {
        return betoneiraRepository.findByNome(nome).stream()
                .map(BetoneiraResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    public List<BetoneiraResponseDTO> findByTipo(String nome) {
        return betoneiraRepository.findByTipo(nome).stream()
                .map(BetoneiraResponseDTO::valueOf)
                .collect(Collectors.toList());
    }
}