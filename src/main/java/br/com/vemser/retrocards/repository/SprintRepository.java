package br.com.vemser.retrocards.repository;

import br.com.vemser.retrocards.entity.SprintEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SprintRepository extends JpaRepository<SprintEntity, Integer> {

//    List<SprintEntity> findAllByEndDateOrderByEndDateDesc(LocalDate endDate);

    @Query(value = "    SELECT s " +
            "             FROM sprints s " +
            "         ORDER BY s.endDate DESC")
    List<SprintEntity> listByEndDateOrderedDesc();
}
