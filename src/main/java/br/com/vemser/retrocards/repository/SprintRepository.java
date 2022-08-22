package br.com.vemser.retrocards.repository;

import br.com.vemser.retrocards.entity.SprintEntity;
import br.com.vemser.retrocards.enums.SprintStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SprintRepository extends JpaRepository<SprintEntity, Integer> {

    List<SprintEntity> findAllByStatusIs(SprintStatus status);
}
