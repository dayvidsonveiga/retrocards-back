package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.page.ItemRetrospective.ItemRetrospectiveCreateDTO;
import br.com.vemser.retrocards.dto.page.ItemRetrospective.ItemRetrospectiveDTO;
import br.com.vemser.retrocards.dto.page.ItemRetrospective.ItemRetrospectiveUpdateDTO;
import br.com.vemser.retrocards.entity.ItemRetrospectiveEntity;
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

    public ItemRetrospectiveDTO update(Integer idItemRetrospective, ItemType itemType, ItemRetrospectiveUpdateDTO itemRetrospectiveUpdateDTO) throws NegociationRulesException {
        itemRetrospectiveUpdateDTO.setType(itemType.name());

        ItemRetrospectiveEntity itemEntityRecovered = findById(idItemRetrospective);
        ItemRetrospectiveEntity itemEntityUpdate = updateToEntity(itemRetrospectiveUpdateDTO);

        if (itemRetrospectiveUpdateDTO.getIdRetrospective() == 0 || itemRetrospectiveUpdateDTO.getIdRetrospective() == null) {
            itemEntityUpdate.setRetrospective(itemEntityRecovered.getRetrospective());
        } else {
            itemEntityUpdate.setRetrospective(retrospectiveService.findById(itemRetrospectiveUpdateDTO.getIdRetrospective()));
        }
        if (itemRetrospectiveUpdateDTO.getTitle() == null) {
            itemEntityUpdate.setTitle(itemEntityRecovered.getTitle());
        }
        if (itemRetrospectiveUpdateDTO.getDescription() == null) {
            itemEntityUpdate.setDescription(itemEntityRecovered.getDescription());
        }

        itemEntityUpdate.setIdItemRetrospective(idItemRetrospective);

        return entityToDTO(itemRetrospectiveRepository.save(itemEntityUpdate));
    }

    public void delete(Integer idItemRetrospective) throws NegociationRulesException {
        ItemRetrospectiveEntity itemEntity = findById(idItemRetrospective);
        if (itemEntity.getRetrospective().getStatus().name().equals(RetrospectiveStatus.IN_PROGRESS.name())) {
            itemRetrospectiveRepository.delete(itemEntity);
        } else {
            throw new NegociationRulesException("Você só pode remover um item de retrospectiva se a mesma estiver em progresso.");
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
        return itemRetrospectiveDTO;
    }
}
