package br.com.vemser.retrocards.repository;

import br.com.vemser.retrocards.entity.KudoCardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface KudoCardRepository extends JpaRepository<KudoCardEntity, Integer> {

    Page<KudoCardEntity> findAllByKudobox_IdKudoBox(Integer idIdKudoBox, Pageable pageable);

    @Query(value = "    SELECT k " +
            "             FROM kudo_cards k " +
            "         ORDER BY k.createDate ASC")
    Page<KudoCardEntity> findAllByCreateDateOrderByCreateDate(Pageable pageable);

    Integer countAllByKudobox_IdKudoBox(Integer idKudoBox);
}
