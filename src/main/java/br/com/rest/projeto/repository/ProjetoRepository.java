package br.com.rest.projeto.repository;

import br.com.rest.projeto.entity.Projeto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
    List<Projeto> findByEmpresaId(Long idEmpresa, Pageable pageable);
}
