package br.unitins.tp1.repository;

import br.unitins.tp1.model.Endereco;
import br.unitins.tp1.model.Cliente; 
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List; 

@ApplicationScoped
public class EnderecoRepository implements PanacheRepository<Endereco> {
    public List<Endereco> findByCliente(Cliente cliente) { 
        return find("cliente", cliente).list();
    }
}