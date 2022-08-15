package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxCreateDTO;
import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxDTO;
import br.com.vemser.retrocards.dto.kudo.kudocard.KudoCardDTO;
import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.dto.retrospective.Retrospective.RetrospectiveDTO;
import br.com.vemser.retrocards.entity.KudoBoxEntity;
import br.com.vemser.retrocards.entity.KudoCardEntity;
import br.com.vemser.retrocards.entity.RetrospectiveEntity;
import br.com.vemser.retrocards.entity.SprintEntity;
import br.com.vemser.retrocards.enums.KudoStatus;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.repository.KudoBoxRepository;
import br.com.vemser.retrocards.repository.KudoCardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class KudoBoxService {

    private final KudoBoxRepository kudoBoxRepository;
    private final SprintService sprintService;
    private final KudoCardRepository kudoCardRepository;
    private final ObjectMapper objectMapper;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public KudoBoxDTO create(KudoBoxCreateDTO kudoBoxCreateDTO) throws NegociationRulesException {
        SprintEntity sprintEntity = sprintService.findById(kudoBoxCreateDTO.getIdSprint());

        kudoBoxCreateDTO.setStatus(KudoStatus.CREATE.name());
        KudoBoxEntity kudoBoxEntity = createToEntity(kudoBoxCreateDTO);
        kudoBoxEntity.setSprint(sprintEntity);

        return entityToDTO(kudoBoxRepository.save(kudoBoxEntity));
    }

    public List<KudoBoxDTO> list() {
        return kudoBoxRepository.findAll().stream()
                .map(this::entityToDTO)
                .toList();
    }

    public PageDTO<KudoBoxDTO> listKudoBoxByIdSprint(Integer idSprint, Integer pagina, Integer registro) throws NegociationRulesException {
        PageRequest pageRequest = PageRequest.of(pagina, registro);
        Page<KudoBoxEntity> page = kudoBoxRepository.findAllBySprint_IdSprint(idSprint, pageRequest);
        if (!page.isEmpty()) {
            List<KudoBoxDTO> retrospectiveDTO = page.getContent().stream()
                    .map(this::entityToDTO)
                    .toList();
            return new PageDTO<>(page.getTotalElements(), page.getTotalPages(), pagina, registro, retrospectiveDTO);
        } else {
            throw new NegociationRulesException("Não foi encontrado nenhuma kudo box associada a sprint.");
        }
    }

    // Util


    public KudoBoxEntity findById(Integer idKudoBox) throws NegociationRulesException {
        return kudoBoxRepository.findById(idKudoBox)
                .orElseThrow(() -> new NegociationRulesException("Kudobox not found"));
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
