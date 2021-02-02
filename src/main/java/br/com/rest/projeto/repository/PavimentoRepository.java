package br.com.rest.projeto.repository;

import br.com.rest.projeto.entity.Pavimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PavimentoRepository extends JpaRepository<Pavimento, Long> {
    List<Pavimento> findByProjetoId(Long idProjeto);
}
