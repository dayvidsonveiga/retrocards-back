package br.com.vemser.retrocards.repository;

import br.com.vemser.retrocards.entity.RetrospectiveEntity;
import br.com.vemser.retrocards.enums.RetrospectiveStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetrospectiveRepository extends JpaRepository<RetrospectiveEntity, Integer> {

    Page<RetrospectiveEntity> findAllBySprint_IdSprint(Integer idSprint, Pageable pageable);

    Boolean existsBySprint_IdSprintAndStatusEquals(Integer idSprint, RetrospectiveStatus status);
}
