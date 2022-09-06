package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.dto.sprint.*;
import br.com.vemser.retrocards.entity.SprintEntity;
import br.com.vemser.retrocards.enums.KudoStatus;
import br.com.vemser.retrocards.enums.RetrospectiveStatus;
import br.com.vemser.retrocards.enums.SprintStatus;
import br.com.vemser.retrocards.exceptions.NegotiationRulesException;
import br.com.vemser.retrocards.repository.KudoBoxRepository;
import br.com.vemser.retrocards.repository.RetrospectiveRepository;
import br.com.vemser.retrocards.repository.SprintRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SprintService {

    private final SprintRepository sprintRepository;
    private final ObjectMapper objectMapper;
    private final KudoBoxRepository kudoBoxRepository;
    private final RetrospectiveRepository retrospectiveRepository;

    public SprintDTO create(SprintCreateDTO sprintCreateDTO) throws NegotiationRulesException {
        log.info("Creating a new sprint ...");

        if (sprintCreateDTO.getStartDate().isAfter(sprintCreateDTO.getEndDate())) {
            throw new NegotiationRulesException("A data final deve ser posterior a data de início.");
        }

        SprintDTO sprintDTO = createToDTO(sprintCreateDTO);
        SprintEntity sprintEntity = dtoToEntity(sprintDTO);
        sprintEntity.setStatus(SprintStatus.IN_PROGRESS);

        log.info("Sprint create successfully!");
        return entityToDTO(sprintRepository.save(sprintEntity));
    }

    public SprintDTO update(Integer idSprint, SprintUpdateDTO sprintUpdateDTO) throws NegotiationRulesException {
        SprintEntity sprintEntityRecovered = findById(idSprint);
        SprintEntity sprintEntityUpdate = updateToEntity(sprintUpdateDTO);

        if (sprintUpdateDTO.getTitle() == null) {
            sprintEntityUpdate.setTitle(sprintEntityRecovered.getTitle());
        }
        if (sprintUpdateDTO.getStartDate() == null) {
            sprintEntityUpdate.setStartDate(sprintEntityRecovered.getStartDate());
        }
        if (sprintUpdateDTO.getEndDate() == null) {
            sprintEntityUpdate.setEndDate(sprintEntityRecovered.getEndDate());
        }

        sprintEntityUpdate.setIdSprint(idSprint);
        sprintEntityUpdate.setStatus(sprintEntityRecovered.getStatus());
        sprintEntityUpdate.setUsers(sprintEntityRecovered.getUsers());
        sprintEntityUpdate.setRetrospectives(sprintEntityRecovered.getRetrospectives());
        sprintEntityUpdate.setKudoboxs(sprintEntityRecovered.getKudoboxs());

        return entityToDTO(sprintRepository.save(sprintEntityUpdate));
    }

    public void delete(Integer idSprint) throws NegotiationRulesException {
        SprintEntity sprintEntity = findById(idSprint);
        sprintRepository.delete(sprintEntity);
    }

    public PageDTO<SprintWithEndDateDTO> listByDateDesc(Integer pagina, Integer registro) throws NegotiationRulesException {
        PageRequest pageRequest = PageRequest.of(pagina, registro, Sort.by("endDate").descending());
        Page<SprintEntity> page = sprintRepository.findAll(pageRequest);
        if (!page.isEmpty()) {
            List<SprintWithEndDateDTO> sprintDTO = page.getContent().stream()
                    .map(this::entityToSprintWithEndDateDTO)
                    .toList();
            return new PageDTO<>(page.getTotalElements(), page.getTotalPages(), pagina, registro, sprintDTO);
        } else {
            throw new NegotiationRulesException("Não foi possível realizar a listagem das sprints.");
        }
    }

    public SprintCheckDTO checkProgressRetrospectiveAndKudobox(Integer idSprint) throws NegotiationRulesException {
        SprintEntity sprintEntity = findById(idSprint);
        SprintCheckDTO sprintCheck = checkDTOToEntity(sprintEntity);

        if (kudoBoxRepository.existsBySprint_IdSprintAndStatusEquals(idSprint, KudoStatus.IN_PROGRESS)) {
            sprintCheck.setVerifyKudoBox(true);
        } else {
            sprintCheck.setVerifyKudoBox(false);
        }
        if (retrospectiveRepository.existsBySprint_IdSprintAndStatusEquals(idSprint, RetrospectiveStatus.IN_PROGRESS)) {
            sprintCheck.setVerifyRetrospective(true);
        } else {
            sprintCheck.setVerifyRetrospective(false);
        }
        return sprintCheck;
    }

    // util

    public SprintEntity findById(Integer idSprint) throws NegotiationRulesException {
        return sprintRepository.findById(idSprint)
                .orElseThrow(() -> new NegotiationRulesException("Sprint não encontrada!"));
    }

    public SprintCheckDTO checkDTOToEntity(SprintEntity sprintEntity) {
        return objectMapper.convertValue(sprintEntity, SprintCheckDTO.class);
    }

    public SprintEntity dtoToEntity(SprintDTO sprintDTO) {
        return objectMapper.convertValue(sprintDTO, SprintEntity.class);
    }

    public SprintDTO entityToDTO(SprintEntity sprintEntity) {
        SprintDTO sprintDTO = objectMapper.convertValue(sprintEntity, SprintDTO.class);
        return sprintDTO;
    }

    public SprintEntity updateToEntity(SprintUpdateDTO sprintUpdateDTO) {
        SprintEntity sprintEntity = new SprintEntity();
        sprintEntity.setTitle(sprintUpdateDTO.getTitle());
        if (sprintUpdateDTO.getStartDate() != null) {
            sprintEntity.setStartDate(sprintUpdateDTO.getStartDate().atTime(LocalTime.now()));
        }
        if (sprintUpdateDTO.getEndDate() != null) {
            sprintEntity.setEndDate(sprintUpdateDTO.getEndDate().atTime(23, 59, 00));
        }
        return sprintEntity;
    }

    public SprintWithEndDateDTO entityToSprintWithEndDateDTO(SprintEntity sprintEntity) {
        return objectMapper.convertValue(sprintEntity, SprintWithEndDateDTO.class);
    }

    public SprintDTO createToDTO(SprintCreateDTO createDTO) {
        SprintDTO dto = new SprintDTO();
        dto.setTitle(createDTO.getTitle());
        dto.setStartDate(createDTO.getStartDate().atTime(LocalTime.now()));
        dto.setEndDate(createDTO.getEndDate().atTime(23, 59, 00));
        return dto;
    }
}
