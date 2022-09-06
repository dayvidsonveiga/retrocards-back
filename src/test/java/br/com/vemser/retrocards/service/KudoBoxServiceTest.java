package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxCreateDTO;
import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxDTO;
import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxUpdateDTO;
import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxWithCountOfItensDTO;
import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.entity.*;
import br.com.vemser.retrocards.enums.KudoStatus;
import br.com.vemser.retrocards.enums.RetrospectiveStatus;
import br.com.vemser.retrocards.exceptions.NegotiationRulesException;
import br.com.vemser.retrocards.repository.KudoBoxRepository;
import br.com.vemser.retrocards.repository.KudoCardRepository;
import br.com.vemser.retrocards.util.CheckDate;
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
public class KudoBoxServiceTest {

    @InjectMocks
    private KudoBoxService kudoBoxService;
    @Mock
    private KudoBoxRepository kudoBoxRepository;
    @Mock
    private KudoCardRepository kudoCardRepository;
    @Mock
    private SprintService sprintService;
    @Mock
    private CheckDate checkDate;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(kudoBoxService, "objectMapper", objectMapper);
    }

    @Test
    public void shouldTestCreateKudoBoxWithSuccess() throws NegotiationRulesException {
        KudoBoxCreateDTO kudoBoxCreateDTO = getKudoBoxCreateDTO();
        KudoBoxEntity kudoBoxEntity = getKudoBoxEntity();
        SprintEntity sprintEntity = getSprintEntity();


        when(sprintService.findById(anyInt())).thenReturn(sprintEntity);
        when(kudoBoxRepository.save(any(KudoBoxEntity.class))).thenReturn(kudoBoxEntity);
        doNothing().when(checkDate).checkDateIsValid(any(LocalDateTime.class), any(LocalDate.class));

        KudoBoxDTO kudoBoxDTO = kudoBoxService.create(kudoBoxCreateDTO);

        assertNotNull(kudoBoxDTO);
        assertEquals(kudoBoxEntity.getIdKudoBox(), kudoBoxDTO.getIdKudoBox());
        assertEquals(kudoBoxEntity.getStatus(), kudoBoxDTO.getStatus());
        assertEquals(kudoBoxEntity.getTitle(), kudoBoxDTO.getTitle());
        assertEquals(kudoBoxEntity.getEndDate(), kudoBoxDTO.getEndDate());
    }

    @Test(expected = NegotiationRulesException.class)
    public void shouldTestCreateKudoBoxWithoutSuccess() throws NegotiationRulesException {
        KudoBoxCreateDTO kudoBoxCreateDTO = getKudoBoxCreateDTO();
        SprintEntity sprintEntity = getSprintEntityWithInProgressKudoBox();

        when(sprintService.findById(anyInt())).thenReturn(sprintEntity);
        when(kudoBoxRepository.existsBySprint_IdSprintAndStatusEquals(anyInt(), any(KudoStatus.class))).thenReturn(true);

        kudoBoxService.create(kudoBoxCreateDTO);
    }

    @Test
    public void shouldTestUpdateWithSucess() throws NegotiationRulesException {
        KudoBoxUpdateDTO kudoBoxUpdateDTO = getKudoBoxUpdateDTO();
        KudoBoxEntity kudoBoxEntity = getKudoBoxEntity();
        KudoBoxUpdateDTO kudoBoxUpdateDTO1 = new KudoBoxUpdateDTO();

        when(kudoBoxRepository.findById(anyInt())).thenReturn(Optional.of(kudoBoxEntity));
        when(kudoBoxRepository.save(any(KudoBoxEntity.class))).thenReturn(kudoBoxEntity);

        KudoBoxDTO kudoBoxDTO = kudoBoxService.update(1, kudoBoxUpdateDTO);
        KudoBoxDTO kudoBoxDTO1 = kudoBoxService.update(1, kudoBoxUpdateDTO1);

        assertNotNull(kudoBoxDTO);
        assertEquals(kudoBoxEntity.getTitle(), kudoBoxDTO.getTitle());
        assertEquals(kudoBoxEntity.getEndDate(), kudoBoxDTO.getEndDate());

        assertNotNull(kudoBoxDTO1);
        assertEquals(kudoBoxEntity.getTitle(), kudoBoxDTO1.getTitle());
        assertEquals(kudoBoxEntity.getEndDate(), kudoBoxDTO1.getEndDate());
    }

    @Test
    public void shouldTestDeleteWithSucess() throws NegotiationRulesException {
        Integer idParaDeletar = 1;
        KudoBoxEntity kudoBoxEntity = getKudoBoxEntity();

        when(kudoBoxRepository.findById(anyInt())).thenReturn(Optional.of(kudoBoxEntity));
        doNothing().when(kudoBoxRepository).delete(any(KudoBoxEntity.class));

        // act
        kudoBoxService.delete(idParaDeletar);

        //assert
        verify(kudoBoxRepository, times(1)).delete(any(KudoBoxEntity.class));
    }

    @Test
    public void shouldTestListKudoBoxByIdSprintWithSuccess() throws NegotiationRulesException {
        Integer pageNumber = 0;
        Integer registerNumber = 10;
        List<KudoBoxEntity> listKudoBox = List.of(getKudoBoxEntity());
        Page<KudoBoxEntity> dtoPage = new PageImpl<>(listKudoBox);

        when(kudoBoxRepository.findAllBySprint_IdSprint(anyInt(), any(Pageable.class))).thenReturn(dtoPage);
        when(kudoCardRepository.countAllByKudobox_IdKudoBox(anyInt())).thenReturn(1);

        PageDTO<KudoBoxWithCountOfItensDTO> kudoBoxDTO = kudoBoxService.listKudoBoxByIdSprint(1, pageNumber, registerNumber);

        //asserts
        assertNotNull(kudoBoxDTO);
        assertEquals(1, kudoBoxDTO.getTotalElements().intValue());
        assertEquals(1, kudoBoxDTO.getTotalPages().intValue());
    }

    @Test(expected = NegotiationRulesException.class)
    public void shouldTestListKudoBoxByIdSprintWithoutSuccess() throws NegotiationRulesException {
        Integer pageNumber = 0;
        Integer registerNumber = 10;
        List<KudoBoxEntity> listKudoBox = new ArrayList<>();
        Page<KudoBoxEntity> dtoPage = new PageImpl<>(listKudoBox);

        when(kudoBoxRepository.findAllBySprint_IdSprint(anyInt(), any(Pageable.class))).thenReturn(dtoPage);

        kudoBoxService.listKudoBoxByIdSprint(1, pageNumber, registerNumber);
    }

    @Test
    public void shouldTestFindByIdWithSuccess() throws NegotiationRulesException {
        KudoBoxEntity kudoBox = getKudoBoxEntity();

        when(kudoBoxRepository.findById(anyInt())).thenReturn(Optional.of(kudoBox));

        KudoBoxDTO kudoBoxDTO = kudoBoxService.listById(1);

        assertNotNull(kudoBoxDTO);
        assertEquals(kudoBox.getIdKudoBox(), kudoBoxDTO.getIdKudoBox());
        assertEquals(kudoBox.getTitle(), kudoBoxDTO.getTitle());
        assertEquals(kudoBox.getStatus(), kudoBoxDTO.getStatus());
        assertEquals(kudoBox.getEndDate(), kudoBoxDTO.getEndDate());
    }

    private static KudoBoxEntity getKudoBoxEntity() {
        KudoBoxEntity kudoBoxEntity = new KudoBoxEntity();
        kudoBoxEntity.setIdKudoBox(1);
        SprintEntity sprintEntity = new SprintEntity();
        sprintEntity.setIdSprint(1);
        sprintEntity.setTitle("Sprint title");
        sprintEntity.setStartDate(LocalDateTime.of(2022, 8, 18, 12, 14));
        sprintEntity.setEndDate(LocalDateTime.of(2022, 8, 25, 12, 20));
        sprintEntity.setUsers(Set.of(getUserEntity()));
        sprintEntity.setRetrospectives(Set.of(getRetrospectiveEntity()));
        kudoBoxEntity.setSprint(sprintEntity);
        kudoBoxEntity.setStatus(KudoStatus.CREATE);
        kudoBoxEntity.setTitle("Kudo box title");
        kudoBoxEntity.setEndDate(LocalDateTime.of(2022, 8, 25, 10, 30));
        kudoBoxEntity.setKudocards(Set.of(getKudoCardEntity()));
        return kudoBoxEntity;
    }

    private static KudoBoxCreateDTO getKudoBoxCreateDTO() {
        KudoBoxCreateDTO kudoBoxCreateDTO = new KudoBoxCreateDTO();
        kudoBoxCreateDTO.setIdSprint(1);
        kudoBoxCreateDTO.setStatus(KudoStatus.CREATE);
        kudoBoxCreateDTO.setTitle("Kudo box title");
        kudoBoxCreateDTO.setEndDate(LocalDate.from(LocalDate.of(2022, 8, 25).atTime(12, 54)));
        return kudoBoxCreateDTO;
    }

    private static KudoBoxUpdateDTO getKudoBoxUpdateDTO() {
        KudoBoxUpdateDTO kudoBoxUpdateDTO = new KudoBoxUpdateDTO();
        kudoBoxUpdateDTO.setTitle("Kudo box title");
        kudoBoxUpdateDTO.setEndDate(LocalDate.from(LocalDate.of(2022, 8, 25).atTime(12, 54)));
        return kudoBoxUpdateDTO;
    }

    private static SprintEntity getSprintEntity() {
        SprintEntity sprintEntity = new SprintEntity();
        sprintEntity.setIdSprint(1);
        sprintEntity.setTitle("Sprint title");
        sprintEntity.setStartDate(LocalDateTime.of(2022, 8, 18, 12, 14));
        sprintEntity.setEndDate(LocalDateTime.of(2022, 8, 25, 12, 20));
        sprintEntity.setUsers(Set.of(getUserEntity()));
        sprintEntity.setRetrospectives(Set.of(getRetrospectiveEntity()));
        KudoBoxEntity kudoBoxEntity = new KudoBoxEntity();
        kudoBoxEntity.setStatus(KudoStatus.CREATE);
        sprintEntity.setKudoboxs(Set.of(kudoBoxEntity));
        return sprintEntity;
    }

    private static SprintEntity getSprintEntityWithInProgressKudoBox() {
        SprintEntity sprintEntity = new SprintEntity();
        sprintEntity.setIdSprint(1);
        sprintEntity.setTitle("Sprint title");
        sprintEntity.setStartDate(LocalDateTime.of(2022, 8, 18, 12, 14));
        sprintEntity.setEndDate(LocalDateTime.of(2022, 8, 25, 12, 20));
        sprintEntity.setUsers(Set.of(getUserEntity()));
        sprintEntity.setRetrospectives(Set.of(getRetrospectiveEntity()));
        KudoBoxEntity kudoBoxEntity = new KudoBoxEntity();
        kudoBoxEntity.setStatus(KudoStatus.IN_PROGRESS);
        sprintEntity.setKudoboxs(Set.of(kudoBoxEntity));
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

    private static RetrospectiveEntity getRetrospectiveEntity() {
        RetrospectiveEntity retrospectiveEntity = new RetrospectiveEntity();
        retrospectiveEntity.setIdRetrospective(1);
        retrospectiveEntity.setStatus(RetrospectiveStatus.CREATE);
        retrospectiveEntity.setTitle("Retrospective title");
        retrospectiveEntity.setOccurredDate(LocalDateTime.of(2022, 8, 18, 8, 24));
        return retrospectiveEntity;
    }

    private static KudoCardEntity getKudoCardEntity() {
        KudoCardEntity kudoCardEntity = new KudoCardEntity();
        kudoCardEntity.setIdKudoCard(1);
        kudoCardEntity.setReceiver("Dayvidson");
        kudoCardEntity.setTitle("Kudo card title");
        kudoCardEntity.setIdCreator(1);
        kudoCardEntity.setSender("Willian");
        kudoCardEntity.setCreateDate(LocalDateTime.now());
        kudoCardEntity.setDescription("Description kudo card");
        return kudoCardEntity;
    }
}
