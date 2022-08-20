package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.dto.sprint.SprintCreateDTO;
import br.com.vemser.retrocards.dto.sprint.SprintDTO;
import br.com.vemser.retrocards.dto.sprint.SprintUpdateDTO;
import br.com.vemser.retrocards.dto.sprint.SprintWithEndDateDTO;
import br.com.vemser.retrocards.entity.*;
import br.com.vemser.retrocards.enums.KudoStatus;
import br.com.vemser.retrocards.enums.RetrospectiveStatus;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.repository.SprintRepository;
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
public class SprintServiceTest {

    @InjectMocks
    private SprintService sprintService;

    @Mock
    private SprintRepository sprintRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(sprintService, "objectMapper", objectMapper);
    }

    @Test
    public void shouldTestCreateSprintWithSuccess() throws NegociationRulesException {
        //setup
        SprintCreateDTO sprintCreateDTO = getSprintCreateDTO();
        SprintEntity sprintEntity = getSprintEntity();

        when(sprintRepository.save(any(SprintEntity.class))).thenReturn(sprintEntity);

        //act
        SprintDTO sprintDTO = sprintService.create(sprintCreateDTO);

        //asserts
        assertNotNull(sprintDTO);
        assertEquals(sprintEntity.getTitle(), sprintDTO.getTitle());
        assertEquals(sprintEntity.getStartDate(), sprintDTO.getStartDate());
        assertEquals(sprintEntity.getEndDate(), sprintDTO.getEndDate());
    }

    @Test(expected = NegociationRulesException.class)
    public void shouldTestCreateSprintWithoutSucess() throws NegociationRulesException {
        //setup
        SprintCreateDTO sprintCreateDTO = getSprintCreateDTO();
        sprintCreateDTO.setStartDate(LocalDate.of(2022, 8, 18));
        sprintCreateDTO.setEndDate(LocalDate.of(2022, 8, 17));
        SprintEntity sprintEntity = getSprintEntity();

        //act
        SprintDTO sprintDTO = sprintService.create(sprintCreateDTO);

        //asserts
        assertNotNull(sprintDTO);
        assertEquals(sprintEntity.getTitle(), sprintDTO.getTitle());
        assertEquals(sprintEntity.getStartDate(), sprintDTO.getStartDate());
        assertEquals(sprintEntity.getEndDate(), sprintDTO.getEndDate());
    }

    @Test
    public void shouldTestUpdateWithSucess() throws NegociationRulesException {
        SprintUpdateDTO sprintUpdateDTO = getSprintUpdateDTO();
        SprintEntity sprintEntity = getSprintEntity();
        SprintUpdateDTO sprintUpdateDTO1 = new SprintUpdateDTO();

        when(sprintRepository.findById(anyInt())).thenReturn(Optional.of(sprintEntity));
        when(sprintRepository.save(any(SprintEntity.class))).thenReturn(sprintEntity);

        SprintDTO sprintDTO = sprintService.update(1, sprintUpdateDTO);
        SprintDTO sprintDTO1 = sprintService.update(1, sprintUpdateDTO1);

        assertNotNull(sprintDTO);
        assertEquals(sprintEntity.getTitle(), sprintDTO.getTitle());
        assertEquals(sprintEntity.getStartDate(), sprintDTO.getStartDate());
        assertEquals(sprintEntity.getEndDate(), sprintDTO.getEndDate());

        assertNotNull(sprintDTO1);
        assertEquals(sprintEntity.getTitle(), sprintDTO1.getTitle());
        assertEquals(sprintEntity.getStartDate(), sprintDTO1.getStartDate());
        assertEquals(sprintEntity.getEndDate(), sprintDTO1.getEndDate());
    }

    @Test
    public void shouldTestDeleteWithSucess() throws NegociationRulesException {
        SprintEntity sprintEntity = getSprintEntity();

        when(sprintRepository.findById(anyInt())).thenReturn(Optional.of(sprintEntity));
        doNothing().when(sprintRepository).delete(any(SprintEntity.class));

        sprintService.delete(1);

        verify(sprintRepository, times(1)).delete(any(SprintEntity.class));
    }

    @Test
    public void shouldTestLisByDateDescWithSucess() throws NegociationRulesException {
        Integer pageNumber = 0;
        Integer register = 10;
        List<SprintEntity> list = List.of(getSprintEntity());
        Page<SprintEntity> dtoPage = new PageImpl<>(list);

        when(sprintRepository.findAll(any(Pageable.class)))
                .thenReturn(dtoPage);

        PageDTO<SprintWithEndDateDTO> pageDTO = sprintService.listByDateDesc(pageNumber, register);

        assertNotNull(pageDTO);
        assertEquals(1, pageDTO.getContent().size());
    }

    @Test(expected = NegociationRulesException.class)
    public void shouldTestLisByDateDescWithoutSucess() throws NegociationRulesException {
        Integer pageNumber = 0;
        Integer register = 10;
        List<SprintEntity> list = new ArrayList<>();
        Page<SprintEntity> dtoPage = new PageImpl<>(list);

        when(sprintRepository.findAll(any(Pageable.class)))
                .thenReturn(dtoPage);

        PageDTO<SprintWithEndDateDTO> pageDTO = sprintService.listByDateDesc(pageNumber, register);

        assertNotNull(pageDTO);
        assertEquals(1, pageDTO.getContent().size());
    }

    @Test
    public void shouldTestFindByIdWithSucess() throws NegociationRulesException {
        SprintEntity sprint = getSprintEntity();

        when(sprintRepository.findById(anyInt())).thenReturn(Optional.of(sprint));

        SprintEntity sprintEntity = sprintService.findById(1);

        assertNotNull(sprintEntity);
        assertEquals(sprint.getTitle(), sprintEntity.getTitle());
        assertEquals(sprint.getIdSprint(), sprintEntity.getIdSprint());
        assertEquals(sprint.getStartDate(), sprintEntity.getStartDate());
    }

    @Test(expected = NegociationRulesException.class)
    public void shouldTestFindByIdWithoutSucess() throws NegociationRulesException {
        SprintEntity sprint = getSprintEntity();

        SprintEntity sprintEntity = sprintService.findById(1);

        assertNotNull(sprintEntity);
        assertEquals(sprint.getTitle(), sprintEntity.getTitle());
        assertEquals(sprint.getIdSprint(), sprintEntity.getIdSprint());
        assertEquals(sprint.getStartDate(), sprintEntity.getStartDate());
    }



    private static SprintCreateDTO getSprintCreateDTO() {
        SprintCreateDTO sprintCreateDTO = new SprintCreateDTO();
        sprintCreateDTO.setTitle("title");
        sprintCreateDTO.setStartDate(LocalDate.of(2022, 8, 18));
        sprintCreateDTO.setEndDate(LocalDate.of(2022, 8, 19));
        return sprintCreateDTO;
    }

    private static SprintUpdateDTO getSprintUpdateDTO() {
        SprintUpdateDTO sprintUpdateDTO = new SprintUpdateDTO();
        sprintUpdateDTO.setTitle("title");
        sprintUpdateDTO.setStartDate(LocalDate.of(2022, 8, 18));
        sprintUpdateDTO.setEndDate(LocalDate.of(2022, 8, 19));
        return sprintUpdateDTO;
    }

    private static SprintWithEndDateDTO getSprintWithEndDateDTO() {
        SprintWithEndDateDTO sprintWithEndDateDTO = new SprintWithEndDateDTO();
        sprintWithEndDateDTO.setIdSprint(1);
        sprintWithEndDateDTO.setTitle("test");
        sprintWithEndDateDTO.setEndDate(LocalDateTime.of(2022, 8, 18, 10,10,10));
        return sprintWithEndDateDTO;
    }

    private static SprintDTO getSprintDTO() {
        SprintDTO sprintDTO = new SprintDTO();
        sprintDTO.setIdSprint(1);
        sprintDTO.setTitle("SprintDTO title");
        sprintDTO.setStartDate(LocalDateTime.of(2022, 8, 18, 8, 30));
        sprintDTO.setEndDate(LocalDateTime.of(2022, 8, 19, 8, 30));
        return sprintDTO;
    }

    private static SprintEntity getSprintEntity() {
        SprintEntity sprintEntity = new SprintEntity();
        sprintEntity.setIdSprint(1);
        sprintEntity.setTitle("Sprint title");
        sprintEntity.setStartDate(LocalDateTime.of(2022, 8, 18, 12, 14));
        sprintEntity.setEndDate(LocalDateTime.of(2022, 8, 25, 12, 20));
        sprintEntity.setUsers(Set.of(getUserEntity()));
        sprintEntity.setKudoboxs(Set.of(getKudoBoxEntity()));
        sprintEntity.setRetrospectives(Set.of(getRetrospectiveEntity()));
        return sprintEntity;
    }

    private static UserEntity getUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setIdUser(1);
        userEntity.setName("Willian");
        userEntity.setEmail("willian@gmail.com");
        userEntity.setPass("123");
        userEntity.setRole(getRolesEntity());
        return userEntity;
    }

    private static RolesEntity getRolesEntity() {
        RolesEntity rolesEntity = new RolesEntity();
        rolesEntity.setIdRoles(1);
        rolesEntity.setRoleName("ROLE_FACILITATOR");
        return rolesEntity;
    }

    private static KudoBoxEntity getKudoBoxEntity() {
        KudoBoxEntity kudoBoxEntity = new KudoBoxEntity();
        kudoBoxEntity.setIdKudoBox(1);
        kudoBoxEntity.setStatus(KudoStatus.CREATE);
        kudoBoxEntity.setTitle("Kudo box title");
        kudoBoxEntity.setEndDate(LocalDateTime.of(2022, 8, 18, 8, 34));
        return kudoBoxEntity;
    }

    private static RetrospectiveEntity getRetrospectiveEntity() {
        RetrospectiveEntity retrospectiveEntity = new RetrospectiveEntity();
        retrospectiveEntity.setIdRetrospective(1);
        retrospectiveEntity.setStatus(RetrospectiveStatus.CREATE);
        retrospectiveEntity.setTitle("Retrospective title");
        retrospectiveEntity.setOccurredDate(LocalDateTime.of(2022, 8, 18, 8, 24));
        return retrospectiveEntity;
    }


}
