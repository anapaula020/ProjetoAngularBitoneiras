package br.unitins.tp1.service;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.tp1.dto.ItemPedidoDTO;
import br.unitins.tp1.dto.PedidoDTO;
import br.unitins.tp1.dto.PedidoResponseDTO;
import br.unitins.tp1.model.Betoneira;
import br.unitins.tp1.model.Endereco;
import br.unitins.tp1.model.EnumStatusPedido;
import br.unitins.tp1.model.ItemPedido;
import br.unitins.tp1.model.Pedido;
import br.unitins.tp1.repository.BetoneiraRepository;
import br.unitins.tp1.repository.ItemPedidoRepository;
import br.unitins.tp1.repository.PedidoRepository;
import br.unitins.tp1.validation.ValidationException;
import br.unitins.tp1.repository.ClienteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.PathParam;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {
    @Inject
    public PedidoRepository pedidoRepository;

    @Inject
    public ItemPedidoRepository itemPedidoRepository;

    @Inject
    private ClienteRepository clienteRepository;

    @Inject
    private BetoneiraRepository betoneiraRepository;

    @Inject
    private JsonWebToken jsonWebToken;

    @Override
    public long count() {
        return pedidoRepository.count();
    }

    @Override
    @Transactional
    public PedidoResponseDTO create(@Valid PedidoDTO pedidoDTO) {
        Pedido pedidoBanco = new Pedido();
        pedidoBanco.setCliente(
                clienteRepository.findById(clienteRepository.findNomeEqual(jsonWebToken.getName()).getId()));
        Endereco endereco = new Endereco();
        endereco.setRua(pedidoDTO.endereco().getRua());
        endereco.setNumero(pedidoDTO.endereco().getNumero());
        endereco.setComplemento(pedidoDTO.endereco().getComplemento());
        endereco.setBairro(pedidoDTO.endereco().getBairro());
        endereco.setCep(pedidoDTO.endereco().getCep());
        pedidoBanco.setEndereco(endereco);
        List<ItemPedido> itens = new ArrayList<>();
        Double total = 0.0;
        for (ItemPedidoDTO itemDTO : pedidoDTO.itens()) {
            Betoneira betoneiraBanco = betoneiraRepository.findById(itemDTO.idBetoneira());
            validarQuantidade(betoneiraBanco.getQuantidadeEstoque(), itemDTO.quantidade());

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setBetoneira(betoneiraBanco);
            itemPedido.setDesconto(itemDTO.desconto());
            itemPedido.setPreco(betoneiraBanco.getPreco());
            itemPedido.setQuantidade(itemDTO.quantidade());

            total += itemPedido.getPreco() / (itemDTO.desconto() / 100 + 1) * itemDTO.quantidade();
            betoneiraBanco.setQuantidadeEstoque(betoneiraBanco.getQuantidadeEstoque() - itemDTO.quantidade());
            itemPedidoRepository.persist(itemPedido);
            itens.add(itemPedido);
        }
        pedidoBanco.setStatusPedido(EnumStatusPedido.PENDENTE);
        pedidoBanco.setPreco(total);
        pedidoBanco.setItens(itens);
        pedidoRepository.persist(pedidoBanco);
        return PedidoResponseDTO.valueOf(pedidoBanco);
    }

    @Override
    @Transactional
    public PedidoResponseDTO update(Long id, PedidoDTO pedidoDTO) {
        Pedido pedidoBanco = pedidoRepository.findById(id);
        if (pedidoBanco == null) {
            throw new ValidationException("id", "Pedido não existe.");
        }
        pedidoBanco.setCliente(
                clienteRepository.findById(clienteRepository.findNomeEqual(jsonWebToken.getName()).getId()));
        Endereco endereco = new Endereco();
        endereco.setRua(pedidoDTO.endereco().getRua());
        endereco.setNumero(pedidoDTO.endereco().getNumero());
        endereco.setComplemento(pedidoDTO.endereco().getComplemento());
        endereco.setBairro(pedidoDTO.endereco().getBairro());
        endereco.setCep(pedidoDTO.endereco().getCep());
        pedidoBanco.setEndereco(endereco);
        List<ItemPedido> itens = new ArrayList<>();
        Double total = 0.0;
        for (ItemPedidoDTO itemDTO : pedidoDTO.itens()) {
            Betoneira betoneiraBanco = betoneiraRepository.findById(itemDTO.idBetoneira());
            validarQuantidade(betoneiraBanco.getQuantidadeEstoque(), itemDTO.quantidade());

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setBetoneira(betoneiraBanco);
            itemPedido.setDesconto(itemDTO.desconto());
            itemPedido.setPreco(betoneiraBanco.getPreco());
            itemPedido.setQuantidade(itemDTO.quantidade());

            total += itemPedido.getPreco() / (itemDTO.desconto() / 100 + 1) * itemDTO.quantidade();
            betoneiraBanco.setQuantidadeEstoque(betoneiraBanco.getQuantidadeEstoque() - itemDTO.quantidade());
            itemPedidoRepository.persist(itemPedido);
            itens.add(itemPedido);
        }
        pedidoBanco.setPreco(total);
        pedidoBanco.setItens(itens);
        pedidoBanco.setStatusPedido(EnumStatusPedido.PENDENTE);
        pedidoBanco.setItens(itens);
        return PedidoResponseDTO.valueOf(pedidoBanco);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        pedidoRepository.deleteById(id);
    }

    @Override
    public List<PedidoResponseDTO> findAll() {
        return pedidoRepository.listAll().stream().map(e -> PedidoResponseDTO.valueOf(e)).toList();
    }

    public void validarQuantidade(Integer quantidadeReal, Integer quantidadePedido) {
        if (quantidadeReal < quantidadePedido) {
            throw new ValidationException("Quantidade", "Quantidade maior que o estoque");
        }
    }

    @Override
    public PedidoResponseDTO findById(@PathParam("id") Long id) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null)
            return null;
        return PedidoResponseDTO.valueOf(pedido);
    }

    @Override
    public List<PedidoResponseDTO> findByEndereco(@PathParam("nome") String nome) {
        return pedidoRepository.findByEndereco(nome).stream().map(e -> PedidoResponseDTO.valueOf(e)).toList();
    }

    public void processarPedido(Long pedidoId, double valor) {
        Pedido pedido = pedidoRepository.findById(pedidoId);
        if (pedido == null) {
            throw new ValidationException("Pedido", "Pedido não encontrado.");
        }
        if (valor < pedido.getPreco()) {
            throw new ValidationException("Valor", "Valor insuficiente.");
        }

    }

    @Override
    public List<PedidoResponseDTO> findComprasByUser(@PathParam("id") Long id) {
        return pedidoRepository.findByCliente(id).stream().map(e -> PedidoResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<PedidoResponseDTO> findMyCompras() {
        return findComprasByUser(clienteRepository.findNomeEqual(jsonWebToken.getName()).getId());
    }

    // @Override
    // @Transactional
    // public Response PagarPeloPix(@Valid PixDTO pix) {
    // Pedido pedidoPagar = pedidoRepository.findById(pix.idPedido());
    // processarPedido(pix.idPedido(), pix.valor());
    // pedidoPagar.setEstadoPamento(EnumStatusPagamento.APROVADO);
    // pedidoPagar.setTipoPagamento(PagamentoTipo.PIX);
    // return Response.ok().build();
    // }

    // @Override
    // @Transactional
    // public Response PagarPeloCredito(@Valid CartaoDTO cartao, int parcelas) {
    // if (parcelas < 1 || parcelas > 12) {
    // throw new ValidationException("Parcelas", "Parcelas inválidas, deve estar
    // entre 1 e 12");
    // }
    // Pedido pedidoPagar = pedidoRepository.findById(cartao.idPedido());
    // processarPedido(cartao.idPedido(), cartao.limite());
    // pedidoPagar.setEstadoPamento(EnumStatusPagamento.PARCELAS);
    // pedidoPagar.setTipoPagamento(PagamentoTipo.CREDITO);
    // return Response.ok().build();
    // }

    // @Override
    // @Transactional
    // public Response PagarPeloDebito(@Valid CartaoDTO cartao) {
    // Pedido pedidoPagar = pedidoRepository.findById(cartao.idPedido());
    // processarPedido(cartao.idPedido(), cartao.limite());
    // pedidoPagar.setEstadoPamento(EnumStatusPagamento.APROVADO);
    // pedidoPagar.setTipoPagamento(PagamentoTipo.DEBITO);
    // return Response.ok().build();
    // }
}