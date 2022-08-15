package br.com.vemser.retrocards.repository;

import br.com.vemser.retrocards.entity.ItemRetrospectiveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRetrospectiveRepository extends JpaRepository<ItemRetrospectiveEntity, Integer> {

    List<ItemRetrospectiveEntity> findAllByRetrospective_IdRetrospective(Integer idRestrospective);
}
