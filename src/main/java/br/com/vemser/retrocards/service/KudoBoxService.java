package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxCreateDTO;
import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxDTO;
import br.com.vemser.retrocards.entity.KudoBoxEntity;
import br.com.vemser.retrocards.entity.SprintEntity;
import br.com.vemser.retrocards.enums.KudoStatus;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.repository.KudoBoxRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KudoBoxService {

    private final KudoBoxRepository kudoBoxRepository;
    private final SprintService sprintService;
    private final ObjectMapper objectMapper;


    public KudoBoxDTO create(KudoBoxCreateDTO kudoBoxCreateDTO) throws NegociationRulesException {
        SprintEntity sprintEntity = sprintService.findById(kudoBoxCreateDTO.getIdSprint());

        kudoBoxCreateDTO.setStatus(KudoStatus.CREATE.name());
        KudoBoxEntity kudoBoxEntity = createToEntity(kudoBoxCreateDTO);
        kudoBoxEntity.setSprint(sprintEntity);

        return entityToDTO(kudoBoxRepository.save(kudoBoxEntity));
    }

    public KudoBoxEntity createToEntity(KudoBoxCreateDTO kudoBoxCreateDTO) {
        return objectMapper.convertValue(kudoBoxCreateDTO, KudoBoxEntity.class);
    }

    public KudoBoxDTO entityToDTO(KudoBoxEntity kudoBoxEntity) {
        KudoBoxDTO kudoBoxDTO = objectMapper.convertValue(kudoBoxEntity, KudoBoxDTO.class);
        kudoBoxDTO.setSprint(kudoBoxEntity.getSprint().getTitle());
        return kudoBoxDTO;
    }
}
