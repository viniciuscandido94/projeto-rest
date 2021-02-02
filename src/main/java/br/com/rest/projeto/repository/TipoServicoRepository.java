package br.com.rest.projeto.repository;

import br.com.rest.projeto.entity.TipoServico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoServicoRepository extends JpaRepository<TipoServico, Long> {
    List<TipoServico> findByProjetoId(Long idProjeto);
    List<TipoServico> findByProjetoIdIsNull();
}
