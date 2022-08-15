package br.com.vemser.retrocards.repository;

import br.com.vemser.retrocards.entity.KudoCardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KudoCardRepository extends JpaRepository<KudoCardEntity, Integer> {

    Page<KudoCardEntity> findAllByKudobox_IdKudoBox(Integer idIdKudoBox, Pageable pageable);
}
