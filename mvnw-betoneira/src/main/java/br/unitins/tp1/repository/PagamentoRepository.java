package br.unitins.tp1.repository;

import br.unitins.tp1.model.Pagamento;
import br.unitins.tp1.model.StatusPagamento;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class PagamentoRepository implements PanacheRepository<Pagamento> {
    public List<Pagamento> findByStatus(StatusPagamento status) {
        return find("status = ?1", status).list();
    }
}