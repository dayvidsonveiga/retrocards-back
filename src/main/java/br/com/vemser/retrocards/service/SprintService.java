package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.dto.sprint.SprintCreateDTO;
import br.com.vemser.retrocards.dto.sprint.SprintDTO;
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

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SprintService {

    private final SprintRepository sprintRepository;
    private final ObjectMapper objectMapper;

    public SprintDTO create(SprintCreateDTO sprintCreateDTO) throws NegociationRulesException {
        log.info("Creating a new sprint ...");
        SprintEntity sprintEntity = createToEntity(sprintCreateDTO);

        if (sprintCreateDTO.getStartDate().isAfter(sprintCreateDTO.getEndDate())) {
            throw new NegociationRulesException("Data is wrong!");
        }

        log.info("Sprint create successfully!");
        return entityToDTO(sprintRepository.save(sprintEntity));
    }

    public PageDTO<SprintDTO> listSprintOrdered(Integer pagina, Integer registro) throws NegociationRulesException {
        PageRequest pageRequest = PageRequest.of(pagina, registro, Sort.by("endDate").descending());
        Page<SprintEntity> page = sprintRepository.findAll(pageRequest);
        if (!page.isEmpty()) {
            List<SprintDTO> sprintDTO = page.getContent().stream()
                    .map(this::entityToDTO)
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

    public SprintDTO entityToDTO(SprintEntity sprintEntity) {
        SprintDTO sprintDTO = objectMapper.convertValue(sprintEntity, SprintDTO.class);
        return sprintDTO;
    }
}
