package br.com.rest.projeto.repository;

import br.com.rest.projeto.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    List<Funcionario> findByEmpresaId(Long idEmpresa);
}
