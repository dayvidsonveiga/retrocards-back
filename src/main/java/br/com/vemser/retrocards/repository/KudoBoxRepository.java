package br.com.vemser.retrocards.repository;

import br.com.vemser.retrocards.entity.KudoBoxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KudoBoxRepository extends JpaRepository<KudoBoxEntity, Integer> {
}
