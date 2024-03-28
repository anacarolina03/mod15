package br.com.ebac.CategoriaMeme.repositories;

import br.com.ebac.CategoriaMeme.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepo extends JpaRepository<Categoria, Long> {
    Categoria findByNome(String nome);
}
