package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.kudo.kudocard.KudoCardCreateDTO;
import br.com.vemser.retrocards.dto.kudo.kudocard.KudoCardDTO;
import br.com.vemser.retrocards.dto.kudo.kudocard.KudoCardUpdateDTO;
import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.entity.KudoBoxEntity;
import br.com.vemser.retrocards.entity.KudoCardEntity;
import br.com.vemser.retrocards.enums.KudoStatus;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.repository.KudoCardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KudoCardService {

    private final KudoCardRepository kudoCardRepository;
    private final KudoBoxService kudoBoxService;
    private final ObjectMapper objectMapper;

    private final UserService userService;

    public KudoCardDTO create(KudoCardCreateDTO kudoCardCreateDTO) throws NegociationRulesException {
        KudoBoxEntity kudoBox = kudoBoxService.findById(kudoCardCreateDTO.getIdKudoBox());

        KudoCardEntity kudoCardEntity = createToEntity(kudoCardCreateDTO);
        kudoCardEntity.setKudobox(kudoBox);
        kudoCardEntity.setCreateDate(LocalDateTime.now());
        kudoCardEntity.setIdCreator(userService.getIdLoggedUser());

        return entityToDTO(kudoCardRepository.save(kudoCardEntity));
    }

    public void delete(Integer idKudoCard) throws NegociationRulesException {
        KudoCardEntity kudoCardEntity = findById(idKudoCard);
        if (kudoCardEntity.getIdCreator().equals(userService.getIdLoggedUser())) {
            if (kudoCardEntity.getKudobox().getStatus().name() == KudoStatus.IN_PROGRESS.name()) {
                kudoCardRepository.delete(kudoCardEntity);
            } else {
                throw new NegociationRulesException("Só é possivel deletar quando a kudo box estiver em progresso.");
            }
        } else {
            throw new NegociationRulesException("Você não é o criador desse kudo card.");
        }
    }

    public PageDTO<KudoCardDTO> listKudoCardByIdKudoBox(Integer idKudoBox, Integer pagina, Integer registro) throws NegociationRulesException {
        PageRequest pageRequest = PageRequest.of(pagina, registro, Sort.by("createDate").ascending());
        Page<KudoCardEntity> page = kudoCardRepository.findAllByKudobox_IdKudoBox(idKudoBox, pageRequest);
        if (!page.isEmpty()) {
            List<KudoCardDTO> kudoCardDTO = page.getContent().stream()
                    .map(this::entityToDTO)
                    .toList();
            return new PageDTO<>(page.getTotalElements(), page.getTotalPages(), pagina, registro, kudoCardDTO);
        } else {
            throw new NegociationRulesException("Não foi encontrado nenhum kudo card associado ao kudo box.");
        }
    }

    public KudoCardDTO listKudoCardById(Integer idKudoCard) throws NegociationRulesException {
        return entityToDTO(findById(idKudoCard));
    }

    public PageDTO<KudoCardDTO> listKudoCardByStartDate(Integer pagina, Integer registro) throws NegociationRulesException {
        PageRequest pageRequest = PageRequest.of(pagina, registro, Sort.by("createDate").ascending());
        Page<KudoCardEntity> page = kudoCardRepository.findAllByCreateDateOrderByCreateDate(pageRequest);
        if (!page.isEmpty()) {
            List<KudoCardDTO> kudoCardDTO = page.getContent().stream()
                    .map(this::entityToDTO)
                    .toList();
            return new PageDTO<>(page.getTotalElements(), page.getTotalPages(), pagina, registro, kudoCardDTO);
        } else {
            throw new NegociationRulesException("Não foi possível realizar a listagem dos kudo cards.");
        }
    }

    // Util

    public KudoCardEntity findById(Integer idKudoCar) throws NegociationRulesException {
        return kudoCardRepository.findById(idKudoCar)
                .orElseThrow(() -> new NegociationRulesException("Kudocard não encontrado"));
    }

    public KudoCardEntity createToEntity(KudoCardCreateDTO kudoCardCreateDTO) {
        return objectMapper.convertValue(kudoCardCreateDTO, KudoCardEntity.class);
    }

    public KudoCardDTO entityToDTO(KudoCardEntity kudoCardEntity) {
        KudoCardDTO kudoCardDTO = objectMapper.convertValue(kudoCardEntity, KudoCardDTO.class);
        return kudoCardDTO;
    }

    public KudoCardEntity updateToEntity(KudoCardUpdateDTO kudoCardUpdateDTO) {
        return objectMapper.convertValue(kudoCardUpdateDTO, KudoCardEntity.class);
    }
}
