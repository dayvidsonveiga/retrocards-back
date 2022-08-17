package br.com.vemser.retrocards.repository;

import br.com.vemser.retrocards.entity.ItemRetrospectiveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRetrospectiveRepository extends JpaRepository<ItemRetrospectiveEntity, Integer> {

    List<ItemRetrospectiveEntity> findAllByRetrospective_IdRetrospective(Integer idRestrospective);
    Integer countAllByRetrospective_IdRetrospective(Integer idRetrospective);
}
