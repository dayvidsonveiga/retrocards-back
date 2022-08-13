package br.com.vemser.retrocards.repository;

import br.com.vemser.retrocards.entity.SprintEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintRepository extends JpaRepository<SprintEntity, Integer> {
}
