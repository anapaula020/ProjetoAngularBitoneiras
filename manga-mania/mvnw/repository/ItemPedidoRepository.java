package mssaat.org.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import mssaat.org.model.Administrador;
import mssaat.org.model.ItemPedido;

@ApplicationScoped
public class ItemPedidoRepository implements PanacheRepository<ItemPedido> {
}