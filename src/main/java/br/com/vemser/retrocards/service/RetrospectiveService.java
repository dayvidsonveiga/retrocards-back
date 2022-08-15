package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.retrospective.Retrospective.RetrospectiveCreateDTO;
import br.com.vemser.retrocards.dto.retrospective.Retrospective.RetrospectiveDTO;
import br.com.vemser.retrocards.entity.RetrospectiveEntity;
import br.com.vemser.retrocards.entity.SprintEntity;
import br.com.vemser.retrocards.enums.RetrospectiveStatus;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.repository.RetrospectiveRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RetrospectiveService {

    private final RetrospectiveRepository retrospectiveRepository;
    private final SprintService sprintService;
    private final ObjectMapper objectMapper;


    public RetrospectiveDTO create(RetrospectiveCreateDTO retrospectiveCreateDTO) throws NegociationRulesException {
        SprintEntity sprintEntity = sprintService.findById(retrospectiveCreateDTO.getIdSprint());

        retrospectiveCreateDTO.setStatus(RetrospectiveStatus.CREATE.name());
        RetrospectiveEntity retrospectiveEntity = createToEntity(retrospectiveCreateDTO);
        retrospectiveEntity.setSprint(sprintEntity);

        return entityToDTO(retrospectiveRepository.save(retrospectiveEntity));
    }

    // Util

    public RetrospectiveEntity findById(Integer idRespective) throws NegociationRulesException {
        return retrospectiveRepository.findById(idRespective)
                .orElseThrow(() -> new NegociationRulesException("Retrospective not found"));
    }

    public RetrospectiveDTO entityToDTO(RetrospectiveEntity retrospectiveEntity) {
        RetrospectiveDTO retrospectiveDTO = objectMapper.convertValue(retrospectiveEntity, RetrospectiveDTO.class);
        retrospectiveDTO.setSprint(retrospectiveEntity.getSprint().getTitle());
        return retrospectiveDTO;
    }

    public RetrospectiveEntity createToEntity(RetrospectiveCreateDTO retrospectiveCreateDTO) {
        return objectMapper.convertValue(retrospectiveCreateDTO, RetrospectiveEntity.class);
    }
}
