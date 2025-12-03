package br.unitins.tp1.repository;

import br.unitins.tp1.model.Endereco;
import br.unitins.tp1.model.Cliente; // Adicionado para Cliente
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List; // Adicionado para List

@ApplicationScoped
public class EnderecoRepository implements PanacheRepository<Endereco> {
    public List<Endereco> findByCliente(Cliente cliente) { // Adicionado
        return find("cliente", cliente).list();
    }
}