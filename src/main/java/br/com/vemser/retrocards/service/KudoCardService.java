package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.kudo.kudocard.KudoCardCreateDTO;
import br.com.vemser.retrocards.dto.kudo.kudocard.KudoCardDTO;
import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.entity.KudoBoxEntity;
import br.com.vemser.retrocards.entity.KudoCardEntity;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.repository.KudoCardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public PageDTO<KudoCardDTO> listKudoCardByIdKudoBox(Integer idKudoBox, Integer pagina, Integer registro) throws NegociationRulesException {
        PageRequest pageRequest = PageRequest.of(pagina, registro);
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

    public PageDTO<KudoCardDTO> listAll(Integer pagina, Integer registro) throws NegociationRulesException {
        PageRequest pageRequest = PageRequest.of(pagina, registro);
        Page<KudoCardEntity> page = kudoCardRepository.findAll(pageRequest);
        if (!page.isEmpty()) {
            List<KudoCardDTO> kudoCardDTO = page.getContent().stream()
                    .map(this::entityToDTO).toList();
            return new PageDTO<>(page.getTotalElements(), page.getTotalPages(), pagina, registro, kudoCardDTO);
        } else {
            throw new NegociationRulesException("Não há registros de Kudo Cards para serem listados!");
        }
    }

    // Util

    public KudoCardEntity createToEntity(KudoCardCreateDTO kudoCardCreateDTO) {
        return objectMapper.convertValue(kudoCardCreateDTO, KudoCardEntity.class);
    }

    public KudoCardDTO entityToDTO(KudoCardEntity kudoCardEntity) {
        KudoCardDTO kudoCardDTO = objectMapper.convertValue(kudoCardEntity, KudoCardDTO.class);
        return kudoCardDTO;
    }
}
