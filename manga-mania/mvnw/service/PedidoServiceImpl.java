package mssaat.org.service;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import mssaat.org.DTO.CartaoDTO;
import mssaat.org.DTO.ItemPedidoDTO;
import mssaat.org.DTO.PedidoDTO;
import mssaat.org.DTO.PedidoResponseDTO;
import mssaat.org.DTO.PixDTO;
import mssaat.org.model.Endereco;
import mssaat.org.model.ItemPedido;
import mssaat.org.model.Manga;
import mssaat.org.model.PagamentoEstado;
import mssaat.org.model.PagamentoTipo;
import mssaat.org.model.Pedido;
import mssaat.org.repository.ItemPedidoRepository;
import mssaat.org.repository.MangaRepository;
import mssaat.org.repository.PedidoRepository;
import mssaat.org.repository.UsuarioRepository;
import mssaat.org.validation.ValidationException;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {
    @Inject
    public PedidoRepository pedidoRepository;

    @Inject
    public ItemPedidoRepository itemPedidoRepository;

    @Inject
    private UsuarioRepository usuarioRepository;

    @Inject
    private MangaRepository mangaRepository;

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
        pedidoBanco.setUsuario(usuarioRepository.findById(usuarioRepository.findNomeEqual(jsonWebToken.getName()).getId()));
        Endereco endereco = new Endereco();
        endereco.setRua(pedidoDTO.endereco().rua());
        endereco.setNumero(pedidoDTO.endereco().numero());
        endereco.setCidade(pedidoDTO.endereco().cidade());
        endereco.setEstado(pedidoDTO.endereco().estado());
        endereco.setCep(pedidoDTO.endereco().cep());
        pedidoBanco.setEndereco(endereco);
        List<ItemPedido> itens = new ArrayList<>();
        Double total =0.0;
        for (ItemPedidoDTO itemDTO : pedidoDTO.itens()) {
            Manga mangaBanco = mangaRepository.findById(itemDTO.idManga());
            validarQuantidade(mangaBanco.getEstoque(), itemDTO.quantidade());
            
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setManga(mangaBanco);
            itemPedido.setDesconto(itemDTO.desconto());
            itemPedido.setPreco(mangaBanco.getPreco());
            itemPedido.setQuantidade(itemDTO.quantidade());
            
            total += itemPedido.getPreco() / (itemDTO.desconto() / 100 + 1) * itemDTO.quantidade();
            mangaBanco.setEstoque(mangaBanco.getEstoque() - itemDTO.quantidade());
            itemPedidoRepository.persist(itemPedido);
            itens.add(itemPedido);
        }
        pedidoBanco.setEstadoPamento(PagamentoEstado.PENDENTE);
        pedidoBanco.setPreco(total);
        pedidoBanco.setItens(itens);
        pedidoRepository.persist(pedidoBanco);
        return PedidoResponseDTO.valueOf(pedidoBanco);
    }

    @Override
    @Transactional
    public void update(Long id, PedidoDTO pedidoDTO) {
        Pedido pedidoBanco = pedidoRepository.findById(id);
        if (pedidoBanco == null) {
            throw new ValidationException("id", "Pedido não existe.");
        }
        pedidoBanco.setUsuario(usuarioRepository.findById(usuarioRepository.findNomeEqual(jsonWebToken.getName()).getId()));
        Endereco endereco = new Endereco();
        endereco.setRua(pedidoDTO.endereco().rua());
        endereco.setNumero(pedidoDTO.endereco().numero());
        endereco.setCidade(pedidoDTO.endereco().cidade());
        endereco.setEstado(pedidoDTO.endereco().estado());
        endereco.setCep(pedidoDTO.endereco().cep());
        pedidoBanco.setEndereco(endereco);
        List<ItemPedido> itens = new ArrayList<>();
        Double total =0.0;
        for (ItemPedidoDTO itemDTO : pedidoDTO.itens()) {
            Manga mangaBanco = mangaRepository.findById(itemDTO.idManga());
            validarQuantidade(mangaBanco.getEstoque(), itemDTO.quantidade());
            
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setManga(mangaBanco);
            itemPedido.setDesconto(itemDTO.desconto());
            itemPedido.setPreco(mangaBanco.getPreco());
            itemPedido.setQuantidade(itemDTO.quantidade());
            
            total += itemPedido.getPreco() / (itemDTO.desconto() / 100 + 1) * itemDTO.quantidade();
            mangaBanco.setEstoque(mangaBanco.getEstoque() - itemDTO.quantidade());
            itemPedidoRepository.persist(itemPedido);
            itens.add(itemPedido);
        }
        pedidoBanco.setPreco(total);
        pedidoBanco.setItens(itens);
        pedidoBanco.setEstadoPamento(PagamentoEstado.PENDENTE);
        pedidoBanco.setItens(itens);
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
        if (quantidadeReal < quantidadePedido){
            throw new ValidationException("Quantidade", "Quantidade maior que o estoque");
        }
    }

    @Override
    public PedidoResponseDTO findById(@PathParam("id") Long id) {
        Pedido pedido = pedidoRepository.findById(id);
        if(pedido == null) return null;
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
        if(valor<pedido.getPreco()){
            throw new ValidationException("Valor", "Valor insuficiente.");
        }

    }

    @Override
    public List<PedidoResponseDTO> findComprasByUser(@PathParam("id") Long id) {
        return pedidoRepository.findComprasByUser(id).stream().map(e -> PedidoResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<PedidoResponseDTO> findMyCompras() {
        return findComprasByUser(usuarioRepository.findNomeEqual(jsonWebToken.getName()).getId());
    }

    @Override
    @Transactional
    public Response PagarPeloPix(@Valid PixDTO pix) {
        Pedido pedidoPagar = pedidoRepository.findById(pix.idPedido());
        processarPedido(pix.idPedido(), pix.valor());
        pedidoPagar.setEstadoPamento(PagamentoEstado.APROVADO);
        pedidoPagar.setTipoPagamento(PagamentoTipo.PIX);
        return Response.ok().build();
    }
    @Override
    @Transactional
    public Response PagarPeloCredito(@Valid CartaoDTO cartao, int parcelas) {
        if(parcelas < 1 || parcelas > 12){
            throw new ValidationException("Parcelas", "Parcelas inválidas, deve estar entre 1 e 12");
        }
        Pedido pedidoPagar = pedidoRepository.findById(cartao.idPedido());
        processarPedido(cartao.idPedido(), cartao.limite());
        pedidoPagar.setEstadoPamento(PagamentoEstado.PARCELAS);
        pedidoPagar.setTipoPagamento(PagamentoTipo.CREDITO);
        return Response.ok().build();
    }
    @Override
    @Transactional
    public Response PagarPeloDebito(@Valid CartaoDTO cartao) {
        Pedido pedidoPagar = pedidoRepository.findById(cartao.idPedido());
        processarPedido(cartao.idPedido(), cartao.limite());
        pedidoPagar.setEstadoPamento(PagamentoEstado.APROVADO);
        pedidoPagar.setTipoPagamento(PagamentoTipo.DEBITO);
        return Response.ok().build();
    }

}