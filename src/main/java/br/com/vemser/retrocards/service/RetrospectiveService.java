package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.dto.retrospective.RetrospectiveWithCountOfItensDTO;
import br.com.vemser.retrocards.dto.retrospective.RetrospectiveCreateDTO;
import br.com.vemser.retrocards.dto.retrospective.RetrospectiveDTO;
import br.com.vemser.retrocards.entity.RetrospectiveEntity;
import br.com.vemser.retrocards.entity.SprintEntity;
import br.com.vemser.retrocards.enums.RetrospectiveStatus;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.repository.ItemRetrospectiveRepository;
import br.com.vemser.retrocards.repository.RetrospectiveRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RetrospectiveService {

    private final RetrospectiveRepository retrospectiveRepository;
    private final SprintService sprintService;
    private final ObjectMapper objectMapper;
    private final ItemRetrospectiveRepository itemRetrospectiveRepository;

//    private final EmailService emailService;


    public RetrospectiveDTO create(RetrospectiveCreateDTO retrospectiveCreateDTO) throws NegociationRulesException {
        SprintEntity sprintEntity = sprintService.findById(retrospectiveCreateDTO.getIdSprint());

        RetrospectiveDTO retrospectiveDTO = createToDTO(retrospectiveCreateDTO);

        RetrospectiveEntity retrospectiveEntity = dtoToEntity(retrospectiveDTO);

        retrospectiveEntity.setSprint(sprintEntity);

        retrospectiveEntity.setStatus(RetrospectiveStatus.CREATE);

        return entityToDTO(retrospectiveRepository.save(retrospectiveEntity));
    }

    public RetrospectiveDTO updateStatus(Integer idRetrospectiva, RetrospectiveStatus retrospectiveStatus) throws NegociationRulesException {
        RetrospectiveEntity retrospectiveEntity = findById(idRetrospectiva);
        SprintEntity sprintEntity = retrospectiveEntity.getSprint();

        if (retrospectiveStatus.name() == RetrospectiveStatus.IN_PROGRESS.name()) {
            if (sprintEntity.getRetrospectives().stream()
                    .anyMatch(retrospective -> retrospective.getStatus().name() == RetrospectiveStatus.IN_PROGRESS.name())){
                throw new NegociationRulesException("Não é possivel atualizar status em uma sprint em progresso!");
            }
        }

        retrospectiveEntity.setStatus(retrospectiveStatus);
        return entityToDTO(retrospectiveRepository.save(retrospectiveEntity));
    }

    public void delete(Integer idRetrospective) throws NegociationRulesException {
        RetrospectiveEntity retrospectiveEntity = findById(idRetrospective);
        retrospectiveRepository.delete(retrospectiveEntity);
    }

    public RetrospectiveDTO listById(Integer id) throws NegociationRulesException {
        return entityToDTO(findById(id));
    }

    public PageDTO<RetrospectiveWithCountOfItensDTO> listRetrospectiveByIdSprint(Integer idSprint, Integer pagina, Integer registro) throws NegociationRulesException {
        PageRequest pageRequest = PageRequest.of(pagina, registro);
        Page<RetrospectiveEntity> page = retrospectiveRepository.findAllBySprint_IdSprint(idSprint, pageRequest);
        if (!page.isEmpty()) {
            List<RetrospectiveWithCountOfItensDTO> retrospectiveDTO = page.getContent().stream()
                    .map(this::entityToRetrospectiveWithCountOfItensDTO)
                    .toList();
            return new PageDTO<>(page.getTotalElements(), page.getTotalPages(), pagina, registro, retrospectiveDTO);
        } else {
            throw new NegociationRulesException("Não foi encontrada nenhuma retrospectiva associada a sprint.");
        }
    }

    // Util

    public RetrospectiveEntity findById(Integer idRespective) throws NegociationRulesException {
        return retrospectiveRepository.findById(idRespective)
                .orElseThrow(() -> new NegociationRulesException("Retrospectiva não encontrada"));
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

//    public RetrospectiveEntity createToEntity(RetrospectiveCreateDTO retrospectiveCreateDTO) {
//        return objectMapper.convertValue(retrospectiveCreateDTO, RetrospectiveEntity.class);
//    }

    public RetrospectiveEntity dtoToEntity(RetrospectiveDTO retrospectiveDTO) {
        return objectMapper.convertValue(retrospectiveDTO, RetrospectiveEntity.class);
    }

    public RetrospectiveDTO createToDTO(RetrospectiveCreateDTO createDTO) {
        RetrospectiveDTO dto = new RetrospectiveDTO();
        dto.setTitle(createDTO.getTitle());
        dto.setOccurredDate(createDTO.getOccurredDate().atTime(LocalTime.now()));
        return dto;
    }
}
