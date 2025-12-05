package br.unitins.tp1.dto;

import br.unitins.tp1.model.Pedido;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoResponseDTO {
    private Long id;
    private LocalDateTime dataPedido;
    private Double totalPedido;
    private Long clienteId;
    private String clienteNome;
    private String clienteEmail;
    private List<ItemPedidoResponseDTO> itens;

    public PedidoResponseDTO() {
    }

    public PedidoResponseDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.dataPedido = pedido.getDataDoPedido();
        this.totalPedido = pedido.getTotalPedido();
        this.clienteId = pedido.getCliente() != null ? pedido.getCliente().getId() : null;
        this.clienteNome = pedido.getCliente() != null ? pedido.getCliente().getNome() : null;
        this.clienteEmail = pedido.getCliente() != null ? pedido.getCliente().getEmail() : null;
        this.itens = pedido.getItens() != null ?
                     pedido.getItens().stream()
                            .map(ItemPedidoResponseDTO::valueOf)
                            .collect(Collectors.toList()) : null;
    }

    public static PedidoResponseDTO valueOf(Pedido pedido) {
        return new PedidoResponseDTO(pedido);
    }

    public Long getId() { return id; }
    public LocalDateTime getDataPedido() { return dataPedido; }
    public Double getTotalPedido() { return totalPedido; }
    public Long getClienteId() { return clienteId; }
    public String getClienteNome() { return clienteNome; }
    public String getClienteEmail() { return clienteEmail; }
    public List<ItemPedidoResponseDTO> getItens() { return itens; }

    public void setId(Long id) { this.id = id; }
    public void setDataPedido(LocalDateTime dataPedido) { this.dataPedido = dataPedido; }
    public void setTotalPedido(Double totalPedido) { this.totalPedido = totalPedido; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public void setClienteNome(String clienteNome) { this.clienteNome = clienteNome; }
    public void setClienteEmail(String clienteEmail) { this.clienteEmail = clienteEmail; }
    public void setItens(List<ItemPedidoResponseDTO> itens) { this.itens = itens; }
}