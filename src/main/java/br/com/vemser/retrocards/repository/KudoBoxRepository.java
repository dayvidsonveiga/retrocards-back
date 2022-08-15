package br.com.vemser.retrocards.repository;

import br.com.vemser.retrocards.entity.KudoBoxEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KudoBoxRepository extends JpaRepository<KudoBoxEntity, Integer> {

    Page<KudoBoxEntity> findAllBySprint_IdSprint(Integer idSprint, Pageable pageable);
}
