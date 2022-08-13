package br.com.vemser.retrocards.repository;

import br.com.vemser.retrocards.entity.KudoCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KudoCardRepository extends JpaRepository<KudoCardEntity, Integer> {
}
