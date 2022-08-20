package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.dto.retrospective.RetrospectiveCreateDTO;
import br.com.vemser.retrocards.dto.retrospective.RetrospectiveDTO;
import br.com.vemser.retrocards.dto.retrospective.RetrospectiveUpdateDTO;
import br.com.vemser.retrocards.dto.retrospective.RetrospectiveWithCountOfItensDTO;
import br.com.vemser.retrocards.dto.sprint.SprintDTO;
import br.com.vemser.retrocards.dto.sprint.SprintUpdateDTO;
import br.com.vemser.retrocards.entity.RetrospectiveEntity;
import br.com.vemser.retrocards.entity.SprintEntity;
import br.com.vemser.retrocards.enums.RetrospectiveStatus;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.repository.ItemRetrospectiveRepository;
import br.com.vemser.retrocards.repository.RetrospectiveRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RetrospectiveServiceTest {

    @InjectMocks
    private RetrospectiveService retrospectiveService;

    @Mock
    private RetrospectiveRepository retrospectiveRepository;

    @Mock
    private SprintService sprintService;

    @Mock
    private ItemRetrospectiveRepository itemRetrospectiveRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(retrospectiveService, "objectMapper", objectMapper);
    }

    @Test
    public void shouldTestCreateWithSucess() throws NegociationRulesException {
        RetrospectiveCreateDTO retrospectiveCreateDTO = getRetrospectiveCreateDTO();
        SprintEntity sprintEntity = getSprintEntity();
        RetrospectiveEntity retrospectiveEntity = getRetrospectiveEntity();

        when(sprintService.findById(anyInt())).thenReturn(sprintEntity);
        when(retrospectiveRepository.save(any(RetrospectiveEntity.class))).thenReturn(retrospectiveEntity);

        RetrospectiveDTO retrospectiveDTO = retrospectiveService.create(retrospectiveCreateDTO);

        assertNotNull(retrospectiveDTO);
        assertEquals(retrospectiveEntity.getTitle(), retrospectiveDTO.getTitle());
        assertEquals(retrospectiveEntity.getStatus().name(), retrospectiveDTO.getStatus().name());
        assertEquals(retrospectiveEntity.getOccurredDate(), retrospectiveDTO.getOccurredDate());
    }

    @Test
    public void shouldTestUpdateStatusWithSucess() throws NegociationRulesException {
        Integer idRetrospectiva = 1;
        RetrospectiveStatus retrospectiveStatus = RetrospectiveStatus.CREATE;
        RetrospectiveEntity retrospectiveEntity = getRetrospectiveEntity();

        when(retrospectiveRepository.findById(anyInt())).thenReturn(Optional.of(retrospectiveEntity));
        when(retrospectiveRepository.save(any(RetrospectiveEntity.class))).thenReturn(retrospectiveEntity);

        RetrospectiveDTO retrospectiveDTO = retrospectiveService.updateStatus(idRetrospectiva, retrospectiveStatus);

        assertNotNull(retrospectiveDTO);
        assertEquals(retrospectiveEntity.getTitle(), retrospectiveDTO.getTitle());
        assertEquals(retrospectiveEntity.getStatus().name(), retrospectiveDTO.getStatus().name());
        assertEquals(retrospectiveEntity.getOccurredDate(), retrospectiveDTO.getOccurredDate());
    }

    @Test(expected = NegociationRulesException.class)
    public void shouldTestUpdateStatusWithoutSucess() throws NegociationRulesException {
        Integer idRetrospectiva = 1;
        RetrospectiveStatus retrospectiveStatus = RetrospectiveStatus.IN_PROGRESS;
        RetrospectiveEntity retrospectiveEntity = getRetrospectiveEntity();

        when(retrospectiveRepository.findById(anyInt())).thenReturn(Optional.of(retrospectiveEntity));

        RetrospectiveDTO retrospectiveDTO = retrospectiveService.updateStatus(idRetrospectiva, retrospectiveStatus);

        assertNotNull(retrospectiveDTO);
        assertEquals(retrospectiveEntity.getTitle(), retrospectiveDTO.getTitle());
        assertEquals(retrospectiveEntity.getStatus().name(), retrospectiveDTO.getStatus().name());
        assertEquals(retrospectiveEntity.getOccurredDate(), retrospectiveDTO.getOccurredDate());
    }

    @Test
    public void shouldTestDeleteWithSucess() throws NegociationRulesException {
        Integer idParaDeletar = 1;
        RetrospectiveEntity retrospectiveEntity = getRetrospectiveEntity();

        when(retrospectiveRepository.findById(anyInt())).thenReturn(Optional.of(retrospectiveEntity));
        doNothing().when(retrospectiveRepository).delete(any(RetrospectiveEntity.class));

        // act
        retrospectiveService.delete(idParaDeletar);

        //assert
        verify(retrospectiveRepository, times(1)).delete(any(RetrospectiveEntity.class));
    }

    @Test
    public void shouldTestUpdateWithSucess() throws NegociationRulesException {
        RetrospectiveUpdateDTO retrospectiveUpdateDTO = getRetrospectiveUpdateDTO();
        RetrospectiveEntity retrospectiveEntity = getRetrospectiveEntity();
        RetrospectiveUpdateDTO retrospectiveUpdateDTO1 = new RetrospectiveUpdateDTO();

        when(retrospectiveRepository.findById(anyInt())).thenReturn(Optional.of(retrospectiveEntity));
        when(retrospectiveRepository.save(any(RetrospectiveEntity.class))).thenReturn(retrospectiveEntity);

        RetrospectiveDTO retrospectiveDTO = retrospectiveService.update(1, retrospectiveUpdateDTO);
        RetrospectiveDTO retrospectiveDTO1 = retrospectiveService.update(1, retrospectiveUpdateDTO1);

        assertNotNull(retrospectiveDTO);
        assertEquals(retrospectiveEntity.getTitle(), retrospectiveDTO.getTitle());
        assertEquals(retrospectiveEntity.getOccurredDate(), retrospectiveDTO.getOccurredDate());
        assertEquals(retrospectiveEntity.getOccurredDate(), retrospectiveDTO.getOccurredDate());

        assertNotNull(retrospectiveDTO1);
        assertEquals(retrospectiveEntity.getTitle(), retrospectiveDTO1.getTitle());
        assertEquals(retrospectiveEntity.getOccurredDate(), retrospectiveDTO1.getOccurredDate());
    }

    @Test
    public void shouldTestListRetrospectiveByIdSprintWithSucess() throws NegociationRulesException {
        Integer pageNumber = 0;
        Integer register = 10;
        List<RetrospectiveEntity> list = List.of(getRetrospectiveEntity());
        Page<RetrospectiveEntity> dtoPage = new PageImpl<>(list);

        when(retrospectiveRepository.findAllBySprint_IdSprint(anyInt(), any(Pageable.class))).thenReturn(dtoPage);

        PageDTO<RetrospectiveWithCountOfItensDTO> pageDTO = retrospectiveService.listRetrospectiveByIdSprint(1, pageNumber, register);

        assertNotNull(pageDTO);
        assertEquals(1, pageDTO.getContent().size());
    }

    @Test(expected = NegociationRulesException.class)
    public void shouldTestListRetrospectiveByIdSprintWithoutSucess() throws NegociationRulesException {
        Integer pageNumber = 0;
        Integer register = 10;
        List<RetrospectiveEntity> list = new ArrayList<>();
        Page<RetrospectiveEntity> dtoPage = new PageImpl<>(list);

        when(retrospectiveRepository.findAllBySprint_IdSprint(anyInt(), any(Pageable.class))).thenReturn(dtoPage);

        PageDTO<RetrospectiveWithCountOfItensDTO> pageDTO = retrospectiveService.listRetrospectiveByIdSprint(1, pageNumber, register);

        assertNotNull(pageDTO);
        assertEquals(1, pageDTO.getContent().size());
    }

    @Test
    public void shouldTesteListByIdWithSucess() throws NegociationRulesException {
        RetrospectiveEntity retrospectiveEntity = getRetrospectiveEntity();

        when(retrospectiveRepository.findById(anyInt())).thenReturn(Optional.of(retrospectiveEntity));

        RetrospectiveDTO retrospectiveDTO = retrospectiveService.listById(1);

        assertNotNull(retrospectiveDTO);
        assertEquals(retrospectiveEntity.getTitle(), retrospectiveDTO.getTitle());
        assertEquals(retrospectiveEntity.getStatus().name(), retrospectiveDTO.getStatus().name());
    }

    private static RetrospectiveCreateDTO getRetrospectiveCreateDTO() {
        RetrospectiveCreateDTO retrospectiveCreateDTO = new RetrospectiveCreateDTO();
        retrospectiveCreateDTO.setIdSprint(1);
        retrospectiveCreateDTO.setTitle("Test");
        retrospectiveCreateDTO.setStatus(RetrospectiveStatus.CREATE.name());
        retrospectiveCreateDTO.setOccurredDate(LocalDate.of(2022, 8, 18));
        return retrospectiveCreateDTO;
    }

    private static RetrospectiveUpdateDTO getRetrospectiveUpdateDTO() {
        RetrospectiveUpdateDTO retrospectiveUpdateDTO = new RetrospectiveUpdateDTO();
        retrospectiveUpdateDTO.setTitle("Test");
        retrospectiveUpdateDTO.setOccurredDate(LocalDate.of(2022, 8, 18));
        return retrospectiveUpdateDTO;
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
        retrospectiveEntity.setItems(null);
        return retrospectiveEntity;
    }

    private static SprintEntity getSprintEntity() {
        SprintEntity sprintEntity = new SprintEntity();
        sprintEntity.setIdSprint(1);
        sprintEntity.setTitle("Sprint title");
        sprintEntity.setStartDate(LocalDateTime.of(2022, 8, 18, 12, 14));
        sprintEntity.setEndDate(LocalDateTime.of(2022, 8, 25, 12, 20));
        sprintEntity.setUsers(null);
        sprintEntity.setKudoboxs(null);
        sprintEntity.setRetrospectives(Set.of(getRetrospectiveEntity()));
        return sprintEntity;
    }
}
