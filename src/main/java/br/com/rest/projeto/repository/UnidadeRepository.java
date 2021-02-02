package br.com.rest.projeto.repository;

import br.com.rest.projeto.entity.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnidadeRepository extends JpaRepository<Unidade, Long> {

    List<Unidade> findByProjetoId(Long idProjeto);
}
