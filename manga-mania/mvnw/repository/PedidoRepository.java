package mssaat.org.repository;

import java.util.List;

import mssaat.org.model.Pedido;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido> {
    public List<Pedido> findByEndereco(String endereco) {
        return find("endereco LIKE ?1", "%" + endereco + "%").list();
    }

    public List<Pedido> findComprasByUser(Long userId) {
        return find("usuario.id = ?1", userId).list();
    }
}