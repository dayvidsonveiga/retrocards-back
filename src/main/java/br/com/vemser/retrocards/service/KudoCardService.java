package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.kudo.kudocard.KudoCardCreateDTO;
import br.com.vemser.retrocards.dto.kudo.kudocard.KudoCardDTO;
import br.com.vemser.retrocards.entity.KudoBoxEntity;
import br.com.vemser.retrocards.entity.KudoCardEntity;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.repository.KudoCardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KudoCardService {

    private final KudoCardRepository kudoCardRepository;
    private final KudoBoxService kudoBoxService;
    private final ObjectMapper objectMapper;

    public KudoCardDTO create(KudoCardCreateDTO kudoCardCreateDTO) throws NegociationRulesException {
        KudoBoxEntity kudoBox = kudoBoxService.findById(kudoCardCreateDTO.getIdKudoBox());

        KudoCardEntity kudoCardEntity = createToEntity(kudoCardCreateDTO);
        kudoCardEntity.setKudobox(kudoBox);

        return entityToDTO(kudoCardRepository.save(kudoCardEntity));
    }

    // Util

    public KudoCardEntity createToEntity(KudoCardCreateDTO kudoCardCreateDTO) {
        return objectMapper.convertValue(kudoCardCreateDTO, KudoCardEntity.class);
    }

    public KudoCardDTO entityToDTO(KudoCardEntity kudoCardEntity) {
        KudoCardDTO kudoCardDTO = objectMapper.convertValue(kudoCardEntity, KudoCardDTO.class);
        kudoCardDTO.setKudoBox(kudoCardEntity.getKudobox().getTitle());
        return kudoCardDTO;
    }
}
