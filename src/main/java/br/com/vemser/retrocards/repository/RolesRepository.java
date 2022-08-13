package br.com.vemser.retrocards.repository;

import br.com.vemser.retrocards.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<RolesEntity, Integer> {
}
