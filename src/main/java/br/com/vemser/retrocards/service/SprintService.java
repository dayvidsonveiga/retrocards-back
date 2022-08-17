package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.dto.sprint.SprintCreateDTO;
import br.com.vemser.retrocards.dto.sprint.SprintDTO;
import br.com.vemser.retrocards.dto.sprint.SprintWithEndDateDTO;
import br.com.vemser.retrocards.entity.SprintEntity;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
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

    public SprintDTO create(SprintCreateDTO sprintCreateDTO) throws NegociationRulesException {
        log.info("Creating a new sprint ...");

        if (sprintCreateDTO.getStartDate().isAfter(sprintCreateDTO.getEndDate())) {
            throw new NegociationRulesException("Data is wrong!");
        }

        SprintDTO sprintDTO = createToDTO(sprintCreateDTO);
        SprintEntity sprintEntity = dtoToEntity(sprintDTO);

        log.info("Sprint create successfully!");
        return entityToDTO(sprintRepository.save(sprintEntity));
    }

    public PageDTO<SprintWithEndDateDTO> listByDateDesc(Integer pagina, Integer registro) throws NegociationRulesException {
        PageRequest pageRequest = PageRequest.of(pagina, registro, Sort.by("endDate").descending());
        Page<SprintEntity> page = sprintRepository.findAll(pageRequest);
        if (!page.isEmpty()) {
            List<SprintWithEndDateDTO> sprintDTO = page.getContent().stream()
                    .map(this::entityToSprintWithEndDateDTO)
                    .toList();
            return new PageDTO<>(page.getTotalElements(), page.getTotalPages(), pagina, registro, sprintDTO);
        } else {
            throw new NegociationRulesException("Não foi possível realizar a listagem das sprints.");
        }
    }

    // util

    public SprintEntity findById(Integer idSprint) throws NegociationRulesException {
        return sprintRepository.findById(idSprint)
                .orElseThrow(() -> new NegociationRulesException("Sprint not found!"));
    }

    public SprintEntity createToEntity(SprintCreateDTO sprintCreateDTO) {
        return objectMapper.convertValue(sprintCreateDTO, SprintEntity.class);
    }

    public SprintEntity dtoToEntity(SprintDTO sprintDTO) {
        return objectMapper.convertValue(sprintDTO, SprintEntity.class);
    }

    public SprintDTO entityToDTO(SprintEntity sprintEntity) {
        SprintDTO sprintDTO = objectMapper.convertValue(sprintEntity, SprintDTO.class);
        return sprintDTO;
    }

    public SprintWithEndDateDTO entityToSprintWithEndDateDTO(SprintEntity sprintEntity) {
        return objectMapper.convertValue(sprintEntity, SprintWithEndDateDTO.class);
    }

    public SprintDTO createToDTO(SprintCreateDTO createDTO) {
        SprintDTO dto = new SprintDTO();
        dto.setTitle(createDTO.getTitle());
        dto.setStartDate(createDTO.getStartDate().atTime(LocalTime.now()));
        dto.setEndDate(createDTO.getEndDate().atTime(LocalTime.now()));
        return dto;
    }
}
