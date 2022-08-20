package br.com.vemser.retrocards.repository;

import br.com.vemser.retrocards.entity.KudoBoxEntity;
import br.com.vemser.retrocards.enums.KudoStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KudoBoxRepository extends JpaRepository<KudoBoxEntity, Integer> {

    Page<KudoBoxEntity> findAllBySprint_IdSprint(Integer idSprint, Pageable pageable);

    Boolean existsBySprint_IdSprintAndStatusEquals(Integer idSprint, KudoStatus status);
}
