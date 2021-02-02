package br.com.rest.projeto.repository;

import br.com.rest.projeto.entity.Foto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FotosRepository extends JpaRepository<Foto, Long> {
    List<Foto> deleteByServicoId(Long id);
    List<Foto> findByServicoId(Long id);
}
