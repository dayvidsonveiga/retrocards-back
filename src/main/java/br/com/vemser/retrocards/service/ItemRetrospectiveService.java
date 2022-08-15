package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxCreateDTO;
import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxDTO;
import br.com.vemser.retrocards.dto.retrospective.ItemRetrospective.ItemRetrospectiveCreateDTO;
import br.com.vemser.retrocards.dto.retrospective.ItemRetrospective.ItemRetrospectiveDTO;
import br.com.vemser.retrocards.dto.retrospective.ItemRetrospective.ItemRetrospectiveUpdateDTO;
import br.com.vemser.retrocards.entity.ItemRetrospectiveEntity;
import br.com.vemser.retrocards.entity.KudoBoxEntity;
import br.com.vemser.retrocards.entity.RetrospectiveEntity;
import br.com.vemser.retrocards.enums.ItemType;
import br.com.vemser.retrocards.enums.RetrospectiveStatus;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.repository.ItemRetrospectiveRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemRetrospectiveService {

    private final ItemRetrospectiveRepository itemRetrospectiveRepository;
    private final RetrospectiveService retrospectiveService;
    private final ObjectMapper objectMapper;

    public ItemRetrospectiveDTO create(ItemRetrospectiveCreateDTO itemRetrospectiveCreateDTO, ItemType itemType) throws NegociationRulesException {
        RetrospectiveEntity retrospectiveEntity = retrospectiveService.findById(itemRetrospectiveCreateDTO.getIdRetrospective());

        itemRetrospectiveCreateDTO.setType(itemType.name());
        ItemRetrospectiveEntity itemEntity = createToEntity(itemRetrospectiveCreateDTO);
        itemEntity.setRetrospective(retrospectiveEntity);

        return entityToDTO(itemRetrospectiveRepository.save(itemEntity));
    }

    public ItemRetrospectiveDTO update(Integer id, ItemType itemType, ItemRetrospectiveUpdateDTO itemRetrospectiveUpdateDTO) throws NegociationRulesException {
        itemRetrospectiveUpdateDTO.setType(itemType.name());

        ItemRetrospectiveEntity itemEntityRecovered = findById(id);
        ItemRetrospectiveEntity itemEntityUpdate = updateToEntity(itemRetrospectiveUpdateDTO);

        if (itemRetrospectiveUpdateDTO.getIdRetrospective() == 0 || itemRetrospectiveUpdateDTO.getIdRetrospective() == null) {
            itemEntityUpdate.setRetrospective(itemEntityRecovered.getRetrospective());
        }
        if (itemRetrospectiveUpdateDTO.getTitle() == null) {
            itemEntityUpdate.setTitle(itemEntityRecovered.getTitle());
        }
        if (itemRetrospectiveUpdateDTO.getDescription() == null) {
            itemEntityUpdate.setDescription(itemEntityRecovered.getDescription());
        }

        itemEntityUpdate.setIdItemRetrospective(id);

        return entityToDTO(itemRetrospectiveRepository.save(itemEntityUpdate));
    }

    public void delete(Integer id) throws NegociationRulesException {
        ItemRetrospectiveEntity itemEntity = findById(id);
        if (itemEntity.getRetrospective().getStatus().name() == RetrospectiveStatus.IN_PROGRESS.name()) {
            itemRetrospectiveRepository.delete(itemEntity);
        } else {
            throw new NegociationRulesException("You can't delete on Retrospective status in progress");
        }
    }

    public List<ItemRetrospectiveDTO> list() {
        return itemRetrospectiveRepository.findAll().stream()
                .map(this::entityToDTO)
                .toList();
    }

    public ItemRetrospectiveDTO listByIdItem(Integer id) throws NegociationRulesException {
        return entityToDTO(findById(id));
    }

    public List<ItemRetrospectiveDTO> listByIdRetrospective(Integer idRetrospective) {
        return findByIdRetrospective(idRetrospective).stream()
                .map(this::entityToDTO)
                .toList();
    }


    // Util

    public ItemRetrospectiveEntity findById(Integer idItemRetrospective) throws NegociationRulesException {
        return itemRetrospectiveRepository.findById(idItemRetrospective)
                .orElseThrow(() -> new NegociationRulesException("Item retrospective not found"));
    }

    public List<ItemRetrospectiveEntity> findByIdRetrospective(Integer idRetrospective) {
        return itemRetrospectiveRepository.findAllByRetrospective_IdRetrospective(idRetrospective);
    }

    public ItemRetrospectiveEntity createToEntity(ItemRetrospectiveCreateDTO itemRetrospectiveCreateDTO) {
        return objectMapper.convertValue(itemRetrospectiveCreateDTO, ItemRetrospectiveEntity.class);
    }

    public ItemRetrospectiveEntity updateToEntity(ItemRetrospectiveUpdateDTO itemRetrospectiveUpdateDTO) {
        return objectMapper.convertValue(itemRetrospectiveUpdateDTO, ItemRetrospectiveEntity.class);
    }

    public ItemRetrospectiveDTO entityToDTO(ItemRetrospectiveEntity itemRetrospectiveEntity) {
        ItemRetrospectiveDTO itemRetrospectiveDTO = objectMapper.convertValue(itemRetrospectiveEntity, ItemRetrospectiveDTO.class);
        itemRetrospectiveDTO.setRetrospective(itemRetrospectiveEntity.getRetrospective().getTitle());
        return itemRetrospectiveDTO;
    }
}
