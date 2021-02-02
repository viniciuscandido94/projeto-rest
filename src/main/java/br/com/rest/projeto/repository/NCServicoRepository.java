package br.com.rest.projeto.repository;

import br.com.rest.projeto.entity.NCServico;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface NCServicoRepository extends JpaRepository<NCServico, Long> {
    List<NCServico> findByProjetoId(Long idProjeto, Pageable pageable);
    List<NCServico> findByProjetoId(Long idProjeto);
}