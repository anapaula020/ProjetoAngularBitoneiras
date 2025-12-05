package br.unitins.tp1.service;

import br.unitins.tp1.dto.TipoBetoneiraDTO;
import br.unitins.tp1.dto.TipoBetoneiraResponseDTO;
import br.unitins.tp1.exception.ServiceException;
import br.unitins.tp1.model.TipoBetoneiraEntity;
import br.unitins.tp1.repository.TipoBetoneiraRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class TipoBetoneiraServiceImpl {

    @Inject
    TipoBetoneiraRepository tipoBetoneiraRepository;

    @Inject
    SecurityContext securityContext;

    @Transactional
    public TipoBetoneiraResponseDTO create(TipoBetoneiraDTO dto) {
        if (!securityContext.isUserInRole("ADMIN")) {
            throw new ServiceException("Usuário não autorizado a criar tipos de betoneira.", Response.Status.FORBIDDEN);
        }

        TipoBetoneiraEntity tipoBetoneira = new TipoBetoneiraEntity();
        tipoBetoneira.setNome(dto.getNome());

        tipoBetoneiraRepository.persist(tipoBetoneira);
        return TipoBetoneiraResponseDTO.valueOf(tipoBetoneira);
    }

    @Transactional
    public TipoBetoneiraResponseDTO update(Long id, TipoBetoneiraDTO dto) {
        if (!securityContext.isUserInRole("ADMIN")) {
            throw new ServiceException("Usuário não autorizado a atualizar tipos de betoneira.", Response.Status.FORBIDDEN);
        }

        TipoBetoneiraEntity tipoBetoneira = tipoBetoneiraRepository.findById(id);
        if (tipoBetoneira == null) {
            throw new ServiceException("Tipo de betoneira não encontrado.", Response.Status.NOT_FOUND);
        }

        tipoBetoneira.setNome(dto.getNome());

        tipoBetoneiraRepository.persist(tipoBetoneira);
        return TipoBetoneiraResponseDTO.valueOf(tipoBetoneira);
    }

    @Transactional
    public void delete(Long id) {
        if (!securityContext.isUserInRole("ADMIN")) {
            throw new ServiceException("Usuário não autorizado a deletar tipos de betoneira.", Response.Status.FORBIDDEN);
        }
        TipoBetoneiraEntity tipoBetoneira = tipoBetoneiraRepository.findById(id);
        if (tipoBetoneira == null) {
            throw new ServiceException("Tipo de betoneira não encontrado.", Response.Status.NOT_FOUND);
        }
        tipoBetoneiraRepository.delete(tipoBetoneira);
    }

    public List<TipoBetoneiraResponseDTO> findAll() {
        return tipoBetoneiraRepository.listAll().stream()
                .map(TipoBetoneiraResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    public TipoBetoneiraResponseDTO findById(Long id) {
        TipoBetoneiraEntity tipoBetoneira = tipoBetoneiraRepository.findById(id);
        if (tipoBetoneira == null) {
            throw new ServiceException("Tipo de betoneira não encontrado.", Response.Status.NOT_FOUND);
        }
        return TipoBetoneiraResponseDTO.valueOf(tipoBetoneira);
    }

    public List<TipoBetoneiraResponseDTO> findByNome(String nome) {
        return tipoBetoneiraRepository.findByNome(nome).stream()
                .map(TipoBetoneiraResponseDTO::valueOf)
                .collect(Collectors.toList());
    }
}