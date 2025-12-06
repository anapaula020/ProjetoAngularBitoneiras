package br.unitins.tp1.service;

import br.unitins.tp1.dto.PagamentoRequestDTO;
import br.unitins.tp1.dto.PagamentoResponseDTO;
import br.unitins.tp1.dto.PixDTO;
import br.unitins.tp1.exception.ServiceException;
import br.unitins.tp1.model.Pagamento;
import br.unitins.tp1.model.Pedido;
import br.unitins.tp1.model.StatusPagamento;
import br.unitins.tp1.model.StatusPedido;
import br.unitins.tp1.model.TipoPagamento;
import br.unitins.tp1.repository.PagamentoRepository;
import br.unitins.tp1.repository.PedidoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.SecurityContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PagamentoServiceImpl implements PagamentoService {
    @Inject
    PagamentoRepository pagamentoRepository;

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    PixService pixService;

    @Inject
    SecurityContext securityContext;
/* 
    @Override
    @Transactional
    public PagamentoResponseDTO create(PagamentoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(dto.getIdPedido());
        if (pedido == null) {
            throw new ServiceException("404", "Pedido não encontrado.");
        }

        if (pedido.getStatusPedido() == StatusPedido.PAGO || pedido.getStatusPedido() == StatusPedido.CANCELADO) {
            throw new ServiceException("400", "Pedido já pago ou cancelado, não pode ser processado novamente.");
        }

        Pagamento pagamento = new Pagamento();
        pagamento.setDataPagamento(LocalDateTime.now());
        pagamento.setPedido(pedido);
        pagamento.setValor(pedido.getTotalPedido());

        TipoPagamento tipoPagamento = TipoPagamento.valueOf(dto.getIdTipoPagamento());
        pagamento.setTipoPagamento(tipoPagamento);
        StatusPagamento statusInicial = StatusPagamento.PROCESSANDO;
        pagamento.setStatus(statusInicial);

        if (tipoPagamento == TipoPagamento.PIX) {
            PixDTO pixDetails = pixService.generatePix(pagamento.getValor(), pedido.getCliente());

            if ("approved".equals(pixDetails.getStatus())) {
                pagamento.setStatus(StatusPagamento.APROVADO);
                pedido.setStatusPedido(StatusPedido.PAGO);
            } else if ("pending".equals(pixDetails.getStatus())) {
                pagamento.setStatus(StatusPagamento.PROCESSANDO);
                pedido.setStatusPedido(StatusPedido.PROCESSANDO);
            } else {
                 pagamento.setStatus(StatusPagamento.REJEITADO);
                 pedido.setStatusPedido(StatusPedido.CANCELADO);
            }

        } else if (tipoPagamento == TipoPagamento.CARTAO_CREDITO) {
            pagamento.setStatus(StatusPagamento.APROVADO);
            pedido.setStatusPedido(StatusPedido.PAGO);
        } else if (tipoPagamento == TipoPagamento.BOLETO) {
            pagamento.setStatus(StatusPagamento.PROCESSANDO);
            pedido.setStatusPedido(StatusPedido.PENDENTE);
        } else {
            throw new ServiceException("400", "Tipo de pagamento não suportado.");
        }

        pagamentoRepository.persist(pagamento);
        pedidoRepository.persist(pedido);

        return PagamentoResponseDTO.valueOf(pagamento);
    }
 */
    @Override
    @Transactional
    public PagamentoResponseDTO update(Long id, PagamentoRequestDTO dto) {
        Pagamento pagamento = pagamentoRepository.findById(id);
        if (pagamento == null) {
            throw new ServiceException("404", "Pagamento não encontrado.");
        }

        String userEmail = securityContext.getUserPrincipal().getName();
        boolean isAdmin = securityContext.isUserInRole("ADMIN");

        if (!isAdmin && !pagamento.getPedido().getCliente().getEmail().equals(userEmail)) {
            throw new ServiceException("403", "Usuário não autorizado a atualizar este pagamento.");
        }

        pagamento.setTipoPagamento(TipoPagamento.valueOf(dto.getIdTipoPagamento()));

        pagamentoRepository.persist(pagamento);
        return PagamentoResponseDTO.valueOf(pagamento);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!securityContext.isUserInRole("ADMIN")) {
            throw new ServiceException("403", "Usuário não autorizado a deletar pagamentos.");
        }

        Pagamento pagamento = pagamentoRepository.findById(id);
        if (pagamento == null) {
            throw new ServiceException("404", "Pagamento não encontrado.");
        }

        pagamentoRepository.delete(pagamento);
    }

    @Override
    public PagamentoResponseDTO findById(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id);
        if (pagamento == null) {
            throw new ServiceException("404", "Pagamento não encontrado.");
        }
        String userEmail = securityContext.getUserPrincipal().getName();
        boolean isAdmin = securityContext.isUserInRole("ADMIN");

        if (!isAdmin && !pagamento.getPedido().getCliente().getEmail().equals(userEmail)) {
            throw new ServiceException("403", "Usuário não autorizado a visualizar este pagamento.");
        }

        return PagamentoResponseDTO.valueOf(pagamento);
    }

    @Override
    public List<PagamentoResponseDTO> findAll() {
        if (!securityContext.isUserInRole("ADMIN")) {
            throw new ServiceException("403", "Usuário não autorizado a listar todos os pagamentos.");
        }
        return pagamentoRepository.listAll().stream()
                .map(PagamentoResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public List<PagamentoResponseDTO> findByStatusPagamento(String status) {
        if (!securityContext.isUserInRole("ADMIN")) {
            throw new ServiceException("403", "Usuário não autorizado a listar pagamentos por status.");
        }
        try {
            StatusPagamento statusEnum = StatusPagamento.valueOf(status.toUpperCase());
            return pagamentoRepository.findByStatus(statusEnum).stream()
                    .map(PagamentoResponseDTO::valueOf)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new ServiceException("400", "Status de pagamento inválido: " + status);
        }
    }

    @Override
    @Transactional
    public void processarPagamento(Long idPagamento, StatusPagamento novoStatus) {
        if (!securityContext.isUserInRole("ADMIN")) {
            throw new ServiceException("403", "Usuário não autorizado a processar pagamentos.");
        }

        Pagamento pagamento = pagamentoRepository.findById(idPagamento);
        if (pagamento == null) {
            throw new ServiceException("404", "Pagamento não encontrado.");
        }

        pagamento.setStatus(novoStatus);
        if (novoStatus == StatusPagamento.APROVADO) {
            pagamento.getPedido().setStatusPedido(StatusPedido.PAGO);
        } else if (novoStatus == StatusPagamento.REJEITADO || novoStatus == StatusPagamento.CANCELADO) {
            pagamento.getPedido().setStatusPedido(StatusPedido.CANCELADO);
        }

        pagamentoRepository.persist(pagamento);
        pedidoRepository.persist(pagamento.getPedido());
    }

    @Override
    public List<PagamentoResponseDTO> findByAuthenticatedUser() {
        String userEmail = securityContext.getUserPrincipal().getName();
        return pagamentoRepository.listAll().stream()
                .filter(p -> p.getPedido().getCliente().getEmail().equals(userEmail))
                .map(PagamentoResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public List<PagamentoResponseDTO> findByNome(String nome) {
        return pagamentoRepository.listAll().stream()
                .filter(pagamento -> pagamento.getPedido() != null &&
                                     pagamento.getPedido().getCliente() != null &&
                                     pagamento.getPedido().getCliente().getNome() != null &&
                                     pagamento.getPedido().getCliente().getNome().toLowerCase().contains(nome.toLowerCase()))
                .map(PagamentoResponseDTO::valueOf)
                .collect(Collectors.toList());
    }
}