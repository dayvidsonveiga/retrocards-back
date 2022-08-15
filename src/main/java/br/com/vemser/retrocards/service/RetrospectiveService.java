package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.retrospective.ItemRetrospective.ItemRetrospectiveDTO;
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

import java.util.List;

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

    public RetrospectiveDTO update(Integer id, RetrospectiveCreateDTO retrospectiveCreateDTO) throws NegociationRulesException {
        listById(id);

        RetrospectiveEntity retrospectiveEntity = createToEntity(retrospectiveCreateDTO);
        retrospectiveEntity.setIdRetrospective(id);

        return entityToDTO(retrospectiveRepository.save(retrospectiveEntity));
    }

    public RetrospectiveDTO updateStatus(Integer idRetrospectiva, RetrospectiveStatus retrospectiveStatus) throws NegociationRulesException {
        RetrospectiveEntity retrospectiveEntity = findById(idRetrospectiva);
        SprintEntity sprintEntity = retrospectiveEntity.getSprint();

        if (retrospectiveStatus.name() == RetrospectiveStatus.IN_PROGRESS.name()) {
            if (sprintEntity.getRetrospectives().stream().anyMatch(retrospective -> retrospective.getStatus().name() == RetrospectiveStatus.IN_PROGRESS.name())){
                throw new NegociationRulesException("Can't start when status is in progress");
            }
        }

        retrospectiveEntity.setStatus(retrospectiveStatus);

        return entityToDTO(retrospectiveRepository.save(retrospectiveEntity));
    }

    public void delete(Integer idRetrospective) throws NegociationRulesException {
        RetrospectiveEntity retrospectiveEntity = findById(idRetrospective);
        retrospectiveRepository.delete(retrospectiveEntity);

    }

    public List<RetrospectiveDTO> list() {
        return retrospectiveRepository.findAll().stream()
                .map(this::entityToDTO)
                .toList();
    }

    public RetrospectiveDTO listById(Integer id) throws NegociationRulesException {
        return entityToDTO(findById(id));
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
