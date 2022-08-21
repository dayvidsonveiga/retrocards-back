package br.com.vemser.retrocards.repository;

import br.com.vemser.retrocards.entity.SprintEntity;
import br.com.vemser.retrocards.enums.SprintStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SprintRepository extends JpaRepository<SprintEntity, Integer> {

    @Query(value = "    SELECT s " +
            "             FROM sprints s " +
            "         ORDER BY s.endDate DESC")
    Page<SprintEntity> listByEndDateOrderedDesc(Pageable pageable);

    List<SprintEntity> findAllByStatusIs(SprintStatus status);
}
