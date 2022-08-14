package br.com.vemser.retrocards.repository;

import br.com.vemser.retrocards.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<RolesEntity, Integer> {

    Optional<RolesEntity> findByRoleName(String role);
}
