package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxCreateDTO;
import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxDTO;
import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxWithCountOfItensDTO;
import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.entity.KudoBoxEntity;
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

import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class KudoBoxService {

    private final KudoBoxRepository kudoBoxRepository;

    private final SprintService sprintService;

    private final ObjectMapper objectMapper;
    private final KudoCardRepository kudoCardRepository;

    public KudoBoxDTO create(KudoBoxCreateDTO kudoBoxCreateDTO) throws NegociationRulesException {
        SprintEntity sprintEntity = sprintService.findById(kudoBoxCreateDTO.getIdSprint());

        if (sprintEntity.getKudoboxs().stream().anyMatch(kudoBoxEntity -> kudoBoxEntity.getStatus() == KudoStatus.IN_PROGRESS)) {
            throw new NegociationRulesException("Não é possível criar kudo box! Outra kudo box em progresso");
        } else {
            KudoBoxDTO kudoBoxDTO = createToDTO(kudoBoxCreateDTO);

            KudoBoxEntity kudoBoxEntity = dtoToEntity(kudoBoxDTO);

            kudoBoxEntity.setSprint(sprintEntity);

            kudoBoxEntity.setStatus(KudoStatus.IN_PROGRESS);

            return entityToDTO(kudoBoxRepository.save(kudoBoxEntity));
        }
    }

//    public KudoBoxDTO updateStatus(Integer idKudoBox, KudoStatus kudoStatus) throws NegociationRulesException {
//        KudoBoxEntity kudoBoxEntity = findById(idKudoBox);
//
//        if (kudoBoxEntity.getStatus().equals(KudoStatus.CREATE)) {
//            kudoBoxEntity.setTitle(kudoBoxEntity.getTitle());
//            kudoBoxEntity.setStatus(KudoStatus.valueOf(kudoStatus.getStatus()));
//        } else {
//            kudoBoxEntity.setTitle(kudoBoxEntity.getTitle());
//            kudoBoxEntity.setStatus(KudoStatus.valueOf(kudoStatus.getStatus()));
//        }
//        return entityToDTO(kudoBoxRepository.save(kudoBoxEntity));
//    }

    public PageDTO<KudoBoxWithCountOfItensDTO> listKudoBoxByIdSprint(Integer idSprint, Integer pagina, Integer registro) throws NegociationRulesException {
        PageRequest pageRequest = PageRequest.of(pagina, registro);
        Page<KudoBoxEntity> page = kudoBoxRepository.findAllBySprint_IdSprint(idSprint, pageRequest);
        if (!page.isEmpty()) {
            List<KudoBoxWithCountOfItensDTO> retrospectiveDTO = page.getContent().stream()
                    .map(this::entityToKudoBoxWithCountOfItensDTO)
                    .toList();
            return new PageDTO<>(page.getTotalElements(), page.getTotalPages(), pagina, registro, retrospectiveDTO);
        } else {
            throw new NegociationRulesException("Não foi encontrado nenhuma kudo box associada a sprint.");
        }
    }

    public KudoBoxDTO listById(Integer id) throws NegociationRulesException {
        return entityToDTO(findById(id));
    }

    // Util

    public KudoBoxEntity findById(Integer idKudoBox) throws NegociationRulesException {
        return kudoBoxRepository.findById(idKudoBox)
                .orElseThrow(() -> new NegociationRulesException("Kudobox não encontrada."));
    }

    public KudoBoxEntity dtoToEntity(KudoBoxDTO kudoBoxDTO) {
        return objectMapper.convertValue(kudoBoxDTO, KudoBoxEntity.class);
    }

    public KudoBoxDTO entityToDTO(KudoBoxEntity kudoBoxEntity) {
        KudoBoxDTO kudoBoxDTO = objectMapper.convertValue(kudoBoxEntity, KudoBoxDTO.class);
        return kudoBoxDTO;
    }

    public KudoBoxWithCountOfItensDTO entityToKudoBoxWithCountOfItensDTO(KudoBoxEntity kudoBoxEntity) {
        KudoBoxWithCountOfItensDTO dtoWithCount = objectMapper.convertValue(kudoBoxEntity, KudoBoxWithCountOfItensDTO.class);
        dtoWithCount.setNumberOfItens(kudoCardRepository.countAllByKudobox_IdKudoBox(dtoWithCount.getIdKudoBox()));
        return dtoWithCount;
    }

    public KudoBoxDTO createToDTO(KudoBoxCreateDTO kudoBoxCreateDTO) {
        KudoBoxDTO kudoBoxDTO = new KudoBoxDTO();
        kudoBoxDTO.setTitle(kudoBoxCreateDTO.getTitle());
        kudoBoxDTO.setStatus(kudoBoxCreateDTO.getStatus());
        kudoBoxDTO.setEndDate(kudoBoxCreateDTO.getEndDate().atTime(LocalTime.now()));
        return kudoBoxDTO;
    }
}
