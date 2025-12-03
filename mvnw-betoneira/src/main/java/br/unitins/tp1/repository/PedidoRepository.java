package br.unitins.tp1.repository;

import br.unitins.tp1.model.Pedido;
import br.unitins.tp1.model.Cliente; // Adicionado para Cliente
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido> {
    // Este método pode ser usado por findByClienteEmail na PedidoService
    public List<Pedido> findByClienteEmail(String email) {
        return find("cliente.email", email).list();
    }

    // Este método pode ser usado por findByCliente na PedidoService
    public List<Pedido> findByClienteId(Long clienteId) {
        return find("cliente.id", clienteId).list();
    }

    // Adicionado para uso em serviços que esperam findByCliente(Cliente)
    public List<Pedido> findByCliente(Cliente cliente) {
        return find("cliente", cliente).list();
    }
}