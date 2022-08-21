package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.ItemRetrospective.ItemRetrospectiveCreateDTO;
import br.com.vemser.retrocards.dto.ItemRetrospective.ItemRetrospectiveDTO;
import br.com.vemser.retrocards.dto.ItemRetrospective.ItemRetrospectiveUpdateDTO;
import br.com.vemser.retrocards.entity.ItemRetrospectiveEntity;
import br.com.vemser.retrocards.entity.RetrospectiveEntity;
import br.com.vemser.retrocards.entity.SprintEntity;
import br.com.vemser.retrocards.enums.ItemType;
import br.com.vemser.retrocards.enums.RetrospectiveStatus;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.repository.ItemRetrospectiveRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)

public class ItemRetrospectiveServiceTest {

    @InjectMocks
    private ItemRetrospectiveService itemRetrospectiveService;

    @Mock
    private ItemRetrospectiveRepository itemRetrospectiveRepository;

    @Mock
    private RetrospectiveService retrospectiveService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(itemRetrospectiveService, "objectMapper", objectMapper);
    }

    @Test
    public void shouldTestCreateWithSucess() throws NegociationRulesException {
        ItemRetrospectiveCreateDTO itemRetrospectiveCreateDTO = getItemRetrospectiveCreateDTO();
        ItemType itemType = ItemType.WORKED;
        ItemRetrospectiveEntity itemRetrospectiveEntity = getItemRetrospectiveEntity();
        RetrospectiveEntity retrospectiveEntity = getRetrospectiveEntity();

        when(retrospectiveService.findById(anyInt())).thenReturn(retrospectiveEntity);
        when(itemRetrospectiveRepository.save(any(ItemRetrospectiveEntity.class))).thenReturn(itemRetrospectiveEntity);

        ItemRetrospectiveDTO itemRetrospectiveDTO = itemRetrospectiveService.create(itemRetrospectiveCreateDTO, itemType);

        assertNotNull(itemRetrospectiveDTO);
        assertEquals(itemRetrospectiveEntity.getIdItemRetrospective(), itemRetrospectiveDTO.getIdItemRetrospective());
        assertEquals(itemRetrospectiveEntity.getTitle(), itemRetrospectiveDTO.getTitle());
        assertEquals(itemRetrospectiveEntity.getDescription(), itemRetrospectiveDTO.getDescription());
    }

    @Test
    public void shouldTestUpdateWithSucess() throws NegociationRulesException {
        ItemRetrospectiveUpdateDTO itemRetrospectiveUpdateDTO = getItemRetrospectiveUpdateDTO();
        ItemType itemType = ItemType.WORKED;
        ItemRetrospectiveEntity itemRetrospectiveEntity = getItemRetrospectiveEntity();

        when(itemRetrospectiveRepository.findById(anyInt())).thenReturn(Optional.of(itemRetrospectiveEntity));
        when(itemRetrospectiveRepository.save(any(ItemRetrospectiveEntity.class))).thenReturn(itemRetrospectiveEntity);

        ItemRetrospectiveDTO itemRetrospectiveDTO = itemRetrospectiveService.update(1, itemType, itemRetrospectiveUpdateDTO);

        assertNotNull(itemRetrospectiveDTO);
        assertEquals(itemRetrospectiveEntity.getIdItemRetrospective(), itemRetrospectiveDTO.getIdItemRetrospective());
        assertEquals(itemRetrospectiveEntity.getTitle(), itemRetrospectiveDTO.getTitle());
        assertEquals(itemRetrospectiveEntity.getDescription(), itemRetrospectiveDTO.getDescription());
    }

    @Test
    public void shouldTestDeleteWithSucess() throws NegociationRulesException {
        Integer idParaDeletar = 1;
        ItemRetrospectiveEntity itemRetrospectiveEntity = getItemRetrospectiveEntity();

        when(itemRetrospectiveRepository.findById(anyInt())).thenReturn(Optional.of(itemRetrospectiveEntity));
        doNothing().when(itemRetrospectiveRepository).delete(any(ItemRetrospectiveEntity.class));

        // act
        itemRetrospectiveService.delete(idParaDeletar);

        //assert
        verify(itemRetrospectiveRepository, times(1)).delete(any(ItemRetrospectiveEntity.class));
    }

    @Test(expected = NegociationRulesException.class)
    public void shouldTestDeleteWithoutSucess() throws NegociationRulesException {
        Integer idParaDeletar = 1;
        ItemRetrospectiveEntity itemRetrospectiveEntity = getItemRetrospectiveEntity();
        itemRetrospectiveEntity.getRetrospective().setStatus(RetrospectiveStatus.CREATE);

        when(itemRetrospectiveRepository.findById(anyInt())).thenReturn(Optional.of(itemRetrospectiveEntity));
        // act
        itemRetrospectiveService.delete(idParaDeletar);

        //assert
        verify(itemRetrospectiveRepository, times(1)).delete(any(ItemRetrospectiveEntity.class));
    }

    @Test
    public void shouldTestListWithSucess() {
        List<ItemRetrospectiveEntity> itemRetrospectiveEntityList = List.of(getItemRetrospectiveEntity());

        when(itemRetrospectiveRepository.findAll()).thenReturn(itemRetrospectiveEntityList);

        List<ItemRetrospectiveDTO> list = itemRetrospectiveService.list();

        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    @Test
    public void shouldTestListByIdRetrospective() throws NegociationRulesException {
        List<ItemRetrospectiveEntity> itemRetrospectiveEntityList = List.of(getItemRetrospectiveEntity());

        when(itemRetrospectiveRepository.findAllByRetrospective_IdRetrospective(anyInt())).thenReturn(itemRetrospectiveEntityList);

        List<ItemRetrospectiveDTO> list = itemRetrospectiveService.listByIdRetrospective(1);

        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    @Test
    public void shouldTestListById() throws NegociationRulesException {
        ItemRetrospectiveEntity itemRetrospectiveEntity = getItemRetrospectiveEntity();

        when(itemRetrospectiveRepository.findById(anyInt())).thenReturn(Optional.of(itemRetrospectiveEntity));

        ItemRetrospectiveDTO itemRetrospectiveDTO = itemRetrospectiveService.listById(itemRetrospectiveEntity.getIdItemRetrospective());

        assertNotNull(itemRetrospectiveDTO);
        assertEquals(itemRetrospectiveEntity.getTitle(), itemRetrospectiveDTO.getTitle());
        assertEquals(itemRetrospectiveEntity.getDescription(), itemRetrospectiveDTO.getDescription());
        assertEquals(itemRetrospectiveEntity.getType(), itemRetrospectiveDTO.getType());
        assertEquals(itemRetrospectiveEntity.getIdItemRetrospective(), itemRetrospectiveDTO.getIdItemRetrospective());
    }

    private static ItemRetrospectiveEntity getItemRetrospectiveEntity() {
        ItemRetrospectiveEntity itemRetrospectiveEntity = new ItemRetrospectiveEntity();
        itemRetrospectiveEntity.setIdItemRetrospective(1);
        itemRetrospectiveEntity.setTitle("test");
        itemRetrospectiveEntity.setDescription("test");
        itemRetrospectiveEntity.setType(ItemType.WORKED);
        RetrospectiveEntity retrospectiveEntity = new RetrospectiveEntity();
        retrospectiveEntity.setStatus(RetrospectiveStatus.IN_PROGRESS);
        itemRetrospectiveEntity.setRetrospective(retrospectiveEntity);
        return itemRetrospectiveEntity;
    }

    private static ItemRetrospectiveCreateDTO getItemRetrospectiveCreateDTO() {
        ItemRetrospectiveCreateDTO itemRetrospectiveCreateDTO = new ItemRetrospectiveCreateDTO();
        itemRetrospectiveCreateDTO.setIdRetrospective(1);
        itemRetrospectiveCreateDTO.setTitle("test");
        itemRetrospectiveCreateDTO.setDescription("test");
        itemRetrospectiveCreateDTO.setType(ItemType.WORKED);
        return itemRetrospectiveCreateDTO;
    }

    public static ItemRetrospectiveUpdateDTO getItemRetrospectiveUpdateDTO() {
        ItemRetrospectiveUpdateDTO itemRetrospectiveUpdateDTO = new ItemRetrospectiveUpdateDTO();
        itemRetrospectiveUpdateDTO.setIdRetrospective(0);
        itemRetrospectiveUpdateDTO.setTitle(null);
        itemRetrospectiveUpdateDTO.setType(null);
        return itemRetrospectiveUpdateDTO;
    }

    private static ItemRetrospectiveDTO getItemRetrospectiveDTO() {
        ItemRetrospectiveDTO itemRetrospectiveDTO = new ItemRetrospectiveDTO();
        itemRetrospectiveDTO.setIdItemRetrospective(1);
        itemRetrospectiveDTO.setTitle("test");
        itemRetrospectiveDTO.setDescription("test");
        itemRetrospectiveDTO.setType(ItemType.WORKED);
        return itemRetrospectiveDTO;
    }

    private static RetrospectiveEntity getRetrospectiveEntity() {
        RetrospectiveEntity retrospectiveEntity = new RetrospectiveEntity();
        retrospectiveEntity.setIdRetrospective(1);
        retrospectiveEntity.setTitle("test");
        retrospectiveEntity.setStatus(RetrospectiveStatus.IN_PROGRESS);
        retrospectiveEntity.setOccurredDate(LocalDateTime.of(2022, 8, 18, 10, 10, 10));
        SprintEntity sprintEntity = new SprintEntity();
        RetrospectiveEntity retrospective = new RetrospectiveEntity();
        retrospective.setStatus(RetrospectiveStatus.IN_PROGRESS);
        sprintEntity.setRetrospectives(Set.of(retrospective));
        retrospectiveEntity.setSprint(sprintEntity);
        ItemRetrospectiveEntity itemRetrospectiveEntity = new ItemRetrospectiveEntity();
        itemRetrospectiveEntity.setIdItemRetrospective(1);
        retrospectiveEntity.setItems(Set.of(itemRetrospectiveEntity));
        return retrospectiveEntity;
    }

}
