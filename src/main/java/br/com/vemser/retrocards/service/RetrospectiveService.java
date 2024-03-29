package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.dto.retrospective.RetrospectiveCreateDTO;
import br.com.vemser.retrocards.dto.retrospective.RetrospectiveDTO;
import br.com.vemser.retrocards.dto.retrospective.RetrospectiveUpdateDTO;
import br.com.vemser.retrocards.dto.retrospective.RetrospectiveWithCountOfItensDTO;
import br.com.vemser.retrocards.entity.RetrospectiveEntity;
import br.com.vemser.retrocards.entity.SprintEntity;
import br.com.vemser.retrocards.enums.RetrospectiveStatus;
import br.com.vemser.retrocards.exceptions.NegotiationRulesException;
import br.com.vemser.retrocards.repository.ItemRetrospectiveRepository;
import br.com.vemser.retrocards.repository.RetrospectiveRepository;
import br.com.vemser.retrocards.util.CheckDate;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RetrospectiveService {

    private final RetrospectiveRepository retrospectiveRepository;
    private final SprintService sprintService;
    private final ObjectMapper objectMapper;
    private final ItemRetrospectiveRepository itemRetrospectiveRepository;
    private final CheckDate checkDate;

    public RetrospectiveDTO create(RetrospectiveCreateDTO retrospectiveCreateDTO) throws NegotiationRulesException {
        SprintEntity sprintEntity = sprintService.findById(retrospectiveCreateDTO.getIdSprint());

        checkDate.checkDateIsValid(sprintEntity.getEndDate(), retrospectiveCreateDTO.getOccurredDate());

        RetrospectiveDTO retrospectiveDTO = createToDTO(retrospectiveCreateDTO);

        RetrospectiveEntity retrospectiveEntity = dtoToEntity(retrospectiveDTO);

        retrospectiveEntity.setSprint(sprintEntity);

        retrospectiveEntity.setStatus(RetrospectiveStatus.CREATE);

        return entityToDTO(retrospectiveRepository.save(retrospectiveEntity));
    }

    public RetrospectiveDTO update(Integer idRetrospective, RetrospectiveUpdateDTO retrospectiveUpdateDTO) throws NegotiationRulesException {
        RetrospectiveEntity retrospectiveEntityRecovered = findById(idRetrospective);
        RetrospectiveEntity retrospectiveEntityUpdate = updateToEntity(retrospectiveUpdateDTO);

        checkDate.checkDateIsValid(retrospectiveEntityRecovered.getSprint().getEndDate(), retrospectiveUpdateDTO.getOccurredDate());

        if (retrospectiveUpdateDTO.getTitle() == null) {
            retrospectiveEntityUpdate.setTitle(retrospectiveEntityRecovered.getTitle());
        }
        if (retrospectiveUpdateDTO.getOccurredDate() == null) {
            retrospectiveEntityUpdate.setOccurredDate(retrospectiveEntityRecovered.getOccurredDate());
        }

        retrospectiveEntityUpdate.setIdRetrospective(idRetrospective);
        retrospectiveEntityUpdate.setSprint(retrospectiveEntityRecovered.getSprint());
        retrospectiveEntityUpdate.setStatus(retrospectiveEntityRecovered.getStatus());
        retrospectiveEntityUpdate.setItems(retrospectiveEntityRecovered.getItems());

        return entityToDTO(retrospectiveRepository.save(retrospectiveEntityUpdate));
    }

    public RetrospectiveDTO updateStatus(Integer idRetrospectiva, RetrospectiveStatus retrospectiveStatus) throws NegotiationRulesException {
        RetrospectiveEntity retrospectiveEntity = findById(idRetrospectiva);
        SprintEntity sprintEntity = retrospectiveEntity.getSprint();

        if (retrospectiveStatus.equals(RetrospectiveStatus.IN_PROGRESS)) {
            if (retrospectiveRepository.existsBySprint_IdSprintAndStatusEquals(sprintEntity.getIdSprint(), RetrospectiveStatus.IN_PROGRESS)) {
                throw new NegotiationRulesException("Não é possivel atualizar status, pois existe uma retrospectiva em progresso!");
            }
        }

        if (retrospectiveEntity.getStatus().equals(RetrospectiveStatus.IN_PROGRESS) && retrospectiveStatus.equals(RetrospectiveStatus.CREATE)) {
            throw new NegotiationRulesException("Não é possível atualizar status de em progresso para criado!");
        }

        if (retrospectiveEntity.getStatus().equals(RetrospectiveStatus.FINISHED) && (retrospectiveStatus.equals(RetrospectiveStatus.CREATE) || retrospectiveStatus.equals(RetrospectiveStatus.IN_PROGRESS))) {
            throw new NegotiationRulesException("Não é possível atualizar status! Retrospectiva finalizada!");
        }

        if (retrospectiveEntity.getStatus().equals(RetrospectiveStatus.CREATE) && retrospectiveStatus.equals(RetrospectiveStatus.FINISHED)) {
            throw new NegotiationRulesException("Não é possível atualizar status de criado para finalizado!");
        }

        retrospectiveEntity.setStatus(retrospectiveStatus);
        return entityToDTO(retrospectiveRepository.save(retrospectiveEntity));
    }

    public void delete(Integer idRetrospective) throws NegotiationRulesException {
        RetrospectiveEntity retrospectiveEntity = findById(idRetrospective);
        retrospectiveRepository.delete(retrospectiveEntity);
    }

    public RetrospectiveDTO listById(Integer id) throws NegotiationRulesException {
        return entityToDTO(findById(id));
    }

    public PageDTO<RetrospectiveWithCountOfItensDTO> listRetrospectiveByIdSprint(Integer idSprint, Integer pagina, Integer registro) throws NegotiationRulesException {
        PageRequest pageRequest = PageRequest.of(pagina, registro);
        Page<RetrospectiveEntity> page = retrospectiveRepository.findAllBySprint_IdSprint(idSprint, pageRequest);
        if (!page.isEmpty()) {
            List<RetrospectiveWithCountOfItensDTO> retrospectiveDTO = page.getContent().stream()
                    .map(this::entityToRetrospectiveWithCountOfItensDTO)
                    .toList();
            return new PageDTO<>(page.getTotalElements(), page.getTotalPages(), pagina, registro, retrospectiveDTO);
        } else {
            throw new NegotiationRulesException("Não foi encontrada nenhuma retrospectiva associada a sprint.");
        }
    }

    // Util

    public RetrospectiveEntity findById(Integer idRespective) throws NegotiationRulesException {
        return retrospectiveRepository.findById(idRespective)
                .orElseThrow(() -> new NegotiationRulesException("Retrospectiva não encontrada"));
    }

    public RetrospectiveDTO entityToDTO(RetrospectiveEntity retrospectiveEntity) {
        RetrospectiveDTO retrospectiveDTO = objectMapper.convertValue(retrospectiveEntity, RetrospectiveDTO.class);
        return retrospectiveDTO;
    }

    public RetrospectiveWithCountOfItensDTO entityToRetrospectiveWithCountOfItensDTO(RetrospectiveEntity retrospectiveEntity) {
        RetrospectiveWithCountOfItensDTO dtoCount = objectMapper.convertValue(retrospectiveEntity, RetrospectiveWithCountOfItensDTO.class);
        dtoCount.setNumberOfItens(itemRetrospectiveRepository.countAllByRetrospective_IdRetrospective(dtoCount.getIdRetrospective()));
        return dtoCount;
    }

    public RetrospectiveEntity updateToEntity(RetrospectiveUpdateDTO retrospectiveUpdateDTO) {
        RetrospectiveEntity retrospectiveEntity = new RetrospectiveEntity();
        retrospectiveEntity.setTitle(retrospectiveUpdateDTO.getTitle());
        if (retrospectiveUpdateDTO.getOccurredDate() != null) {
            retrospectiveEntity.setOccurredDate(retrospectiveUpdateDTO.getOccurredDate().atTime(23, 59, 00));
        }
        return retrospectiveEntity;
    }

    public RetrospectiveEntity dtoToEntity(RetrospectiveDTO retrospectiveDTO) {
        return objectMapper.convertValue(retrospectiveDTO, RetrospectiveEntity.class);
    }

    public RetrospectiveDTO createToDTO(RetrospectiveCreateDTO createDTO) {
        RetrospectiveDTO dto = new RetrospectiveDTO();
        dto.setTitle(createDTO.getTitle());
        dto.setOccurredDate(createDTO.getOccurredDate().atTime(23, 59, 00));
        return dto;
    }
}
