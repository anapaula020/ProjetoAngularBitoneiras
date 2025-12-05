package br.unitins.tp1.repository;

import br.unitins.tp1.model.Pedido;
import br.unitins.tp1.model.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido> {
    public List<Pedido> findByClienteEmail(String email) {
        return find("cliente.email", email).list();
    }

    public List<Pedido> findByClienteId(Long clienteId) {
        return find("cliente.id", clienteId).list();
    }

    public List<Pedido> findByCliente(Cliente cliente) {
        return find("cliente", cliente).list();
    }
}