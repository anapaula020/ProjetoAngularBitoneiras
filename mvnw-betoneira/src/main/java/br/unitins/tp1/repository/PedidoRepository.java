package br.unitins.tp1.repository;

import br.unitins.tp1.model.Pedido;
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

    public List<Pedido> findByCliente(Long id) {
        return find("cliente", id).list();
    }

    public List<Pedido> findByEndereco(String endereco) {
        return find("endereco", endereco).list();
    }
}