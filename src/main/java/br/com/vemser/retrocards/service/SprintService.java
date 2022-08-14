package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.sprint.SprintCreateDTO;
import br.com.vemser.retrocards.dto.sprint.SprintDTO;
import br.com.vemser.retrocards.entity.SprintEntity;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.repository.SprintRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SprintService {

    private final SprintRepository sprintRepository;

    private final ObjectMapper objectMapper;

    public List<SprintDTO> listSprintOrdered() throws NegociationRulesException {
        log.info("Listando sprints pela data de conclusão...");
        if(!sprintRepository.listByEndDateOrderedDesc().isEmpty()) {
            return sprintRepository.listByEndDateOrderedDesc().stream()
                    .map(this::sprintEntityToDTO)
                    .collect(Collectors.toList());
        } else {
            throw new NegociationRulesException("Não foi possível realizar a lista de sprints.");
        }
    }

    public SprintDTO create(SprintCreateDTO sprintCreateDTO) throws NegociationRulesException {
        log.info("Criando nova sprint...");

        SprintEntity sprintEntity = objectMapper.convertValue(sprintCreateDTO, SprintEntity.class);

        log.info("Sprint criada com sucesso!");
        return sprintEntityToDTO(sprintRepository.save(sprintEntity));
    }

    public SprintEntity sprintDTOToEntity(SprintDTO sprintDTO) {
        return objectMapper.convertValue(sprintDTO, SprintEntity.class);
    }

    public SprintDTO sprintEntityToDTO(SprintEntity sprintEntity) {
        return objectMapper.convertValue(sprintEntity, SprintDTO.class);
    }
}
