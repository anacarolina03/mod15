package br.com.ebac.meme.Repository;

import br.com.ebac.meme.Entitie.Meme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemeRepository extends JpaRepository<Meme,Long> {

    @Query("SELECT m FROM Meme m WHERE m.usuario = :usuario")
    List<Meme> buscaMemesPorUsuario(@Param("usuario") String usuario);

    @Query("SELECT m FROM Meme m WHERE m.categoria = :categoria")
    List<Meme> MemePorCategoria(@Param("categoria") String categoria);
}


