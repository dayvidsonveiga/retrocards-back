package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxDTO;
import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxUpdateDTO;
import br.com.vemser.retrocards.dto.kudo.kudocard.KudoCardCreateDTO;
import br.com.vemser.retrocards.dto.kudo.kudocard.KudoCardDTO;
import br.com.vemser.retrocards.dto.kudo.kudocard.KudoCardUpdateDTO;
import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.dto.user.UserDTO;
import br.com.vemser.retrocards.entity.*;
import br.com.vemser.retrocards.enums.KudoStatus;
import br.com.vemser.retrocards.enums.RetrospectiveStatus;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.repository.KudoBoxRepository;
import br.com.vemser.retrocards.repository.KudoCardRepository;
import br.com.vemser.retrocards.repository.UserRepository;
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
public class KudoCardServiceTest {

    @InjectMocks
    private KudoCardService kudoCardService;

    @Mock
    private KudoCardRepository kudoCardRepository;

    @Mock
    private KudoBoxService kudoBoxService;

    @Mock
    private UserService userService;

    @Mock
    private KudoBoxRepository kudoBoxRepository;

    @Mock
    private UserRepository userRepository;



    @Mock
    private LogginService logginService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(kudoCardService, "objectMapper", objectMapper);
    }

    @Test
    public void shouldTestCreateKudoCardWithSuccess() throws NegociationRulesException {
        KudoCardCreateDTO kudoCardCreateDTOAnonymous = getKudoCardCreateDTO();
        kudoCardCreateDTOAnonymous.setAnonymous(true);
        KudoCardCreateDTO kudoCardCreateDTO = getKudoCardCreateDTO();
        KudoCardEntity kudoCardEntity = getKudoCardEntity();
        UserEntity userEntity = getUserEntity();
        UserDTO userDTO = getUserDTO();

        when(userService.getIdLoggedUser()).thenReturn(userEntity.getIdUser());
        when(userService.getLoggedUser()).thenReturn(userDTO);
        when(kudoCardRepository.save(any(KudoCardEntity.class))).thenReturn(kudoCardEntity);

        KudoCardDTO kudoCardDTO = kudoCardService.create(kudoCardCreateDTO);
        KudoCardDTO kudoCardDTO1 = kudoCardService.create(kudoCardCreateDTOAnonymous);

        assertNotNull(kudoCardDTO);
        assertNotNull(kudoCardDTO1);
    }

    @Test
    public void shouldTestUpdateWithSucess() throws NegociationRulesException {
        KudoCardUpdateDTO kudoCardUpdateDTO = getKudoCardUpdateDTO();
        KudoCardEntity kudoCardEntity = getKudoCardEntity();
        KudoCardUpdateDTO kudoCardUpdateDTO1 = new KudoCardUpdateDTO();

        when(kudoCardRepository.findById(anyInt())).thenReturn(Optional.of(kudoCardEntity));
        when(kudoCardRepository.save(any(KudoCardEntity.class))).thenReturn(kudoCardEntity);
        when(userService.getIdLoggedUser()).thenReturn(1);

        KudoCardDTO kudoCardDTO = kudoCardService.update(1, kudoCardUpdateDTO);
        KudoCardDTO kudoCardDTO1 = kudoCardService.update(1, kudoCardUpdateDTO1);

        assertNotNull(kudoCardDTO);
        assertEquals(kudoCardEntity.getTitle(), kudoCardDTO.getTitle());
        assertEquals(kudoCardEntity.getCreateDate(), kudoCardDTO.getCreateDate());

        assertNotNull(kudoCardDTO1);
        assertEquals(kudoCardEntity.getTitle(), kudoCardDTO1.getTitle());
        assertEquals(kudoCardEntity.getCreateDate(), kudoCardDTO1.getCreateDate());
    }

    @Test
    public void shouldTestDeleteKudoCardWithSuccess() throws NegociationRulesException {
        KudoCardEntity kudoCardEntity = getKudoCardEntity();
        UserEntity userEntity = getUserEntity();

        when(userService.getIdLoggedUser()).thenReturn(userEntity.getIdUser());
        when(kudoCardRepository.findById(anyInt())).thenReturn(Optional.of(kudoCardEntity));

        kudoCardService.delete(kudoCardEntity.getIdKudoCard());

        verify(kudoCardRepository, times(1)).delete(any(KudoCardEntity.class));
    }

    @Test(expected = NegociationRulesException.class)
    public void shouldTestDeleteKudoCardWithoutSuccessFirstThrow() throws NegociationRulesException {
        KudoCardEntity kudoCardEntity = getKudoCardEntity();
        kudoCardEntity.getKudobox().setStatus(KudoStatus.CREATE);
        UserEntity userEntity = getUserEntity();

        when(userService.getIdLoggedUser()).thenReturn(userEntity.getIdUser());
        when(kudoCardRepository.findById(anyInt())).thenReturn(Optional.of(kudoCardEntity));

        kudoCardService.delete(kudoCardEntity.getIdKudoCard());
    }

    @Test(expected = NegociationRulesException.class)
    public void shouldTestDeleteKudoCardWithoutSuccessSecondThrow() throws NegociationRulesException {
        KudoCardEntity kudoCardEntity = getKudoCardEntity();
        kudoCardEntity.getKudobox().setStatus(KudoStatus.CREATE);
        UserEntity userEntity = getUserEntity();
        userEntity.setIdUser(2);

        when(userService.getIdLoggedUser()).thenReturn(userEntity.getIdUser());
        when(kudoCardRepository.findById(anyInt())).thenReturn(Optional.of(kudoCardEntity));

        kudoCardService.delete(kudoCardEntity.getIdKudoCard());
    }

    @Test
    public void shouldTestListKudoCardByIdKudoBox() throws NegociationRulesException {
        Integer pageNumber = 0;
        Integer registerNumber = 10;
        List<KudoCardEntity> listKudoCard = List.of(getKudoCardEntity());
        Page<KudoCardEntity> dtoPage = new PageImpl<>(listKudoCard);

        when(kudoCardRepository.findAllByKudobox_IdKudoBox(anyInt(), any(Pageable.class))).thenReturn(dtoPage);

        PageDTO<KudoCardDTO> kudoCardDTO = kudoCardService.listKudoCardByIdKudoBox(1, pageNumber, registerNumber);

        assertNotNull(kudoCardDTO);
        assertEquals(1, kudoCardDTO.getTotalElements().intValue());
        assertEquals(1, kudoCardDTO.getTotalPages().intValue());
    }

    @Test(expected = NegociationRulesException.class)
    public void shouldTestListKudoCardByIdKudoBoxWithoutSuccess() throws NegociationRulesException {
        Integer pageNumber = 0;
        Integer registerNumber = 10;
        List<KudoCardEntity> listKudoCard = new ArrayList<>();
        Page<KudoCardEntity> dtoPage = new PageImpl<>(listKudoCard);

        when(kudoCardRepository.findAllByKudobox_IdKudoBox(anyInt(), any(Pageable.class))).thenReturn(dtoPage);

        kudoCardService.listKudoCardByIdKudoBox(1, pageNumber, registerNumber);
    }

    private static KudoCardCreateDTO getKudoCardCreateDTO() {
        KudoCardCreateDTO kudoCardCreateDTO = new KudoCardCreateDTO();
        kudoCardCreateDTO.setIdKudoBox(1);
        kudoCardCreateDTO.setTitle("Kudo card title");
        kudoCardCreateDTO.setReceiver("Dayvidson");
        kudoCardCreateDTO.setDescription("Kudo card description");
        kudoCardCreateDTO.setAnonymous(false);
        return kudoCardCreateDTO;
    }

    private static KudoCardEntity getKudoCardEntity() {
        KudoCardEntity kudoCardEntity = new KudoCardEntity();
        kudoCardEntity.setIdKudoCard(1);
        kudoCardEntity.setIdCreator(1);
        kudoCardEntity.setCreateDate(LocalDateTime.now());
        kudoCardEntity.setSender(getUserEntity().getName());
        kudoCardEntity.setReceiver("Dayvidson");
        kudoCardEntity.setTitle("Kudo card title");
        kudoCardEntity.setDescription("Kudo card description");

        KudoBoxEntity kudoBoxEntity = new KudoBoxEntity();
        kudoBoxEntity.setStatus(KudoStatus.IN_PROGRESS);

        kudoCardEntity.setKudobox(kudoBoxEntity);
        return kudoCardEntity;
    }

    private static KudoCardUpdateDTO getKudoCardUpdateDTO() {
        KudoCardUpdateDTO kudoCardUpdateDTO = new KudoCardUpdateDTO();

        kudoCardUpdateDTO.setTitle("Kudo card title");
        kudoCardUpdateDTO.setDescription("Kudo card description");

        return kudoCardUpdateDTO;
    }

    private static KudoBoxEntity getKudoBoxEntity() {
        KudoBoxEntity kudoBoxEntity = new KudoBoxEntity();
        kudoBoxEntity.setIdKudoBox(1);
        kudoBoxEntity.setEndDate(LocalDateTime.of(2022, 8, 25, 10, 45));
        kudoBoxEntity.setStatus(KudoStatus.CREATE);
        kudoBoxEntity.setTitle("Kudo box title");
        kudoBoxEntity.setSprint(getSprintEntity());
        return kudoBoxEntity;
    }

    private static SprintEntity getSprintEntity() {
        SprintEntity sprintEntity = new SprintEntity();
        sprintEntity.setIdSprint(1);
        sprintEntity.setTitle("Sprint title");
        sprintEntity.setStartDate(LocalDateTime.of(2022, 8, 18, 12, 14));
        sprintEntity.setEndDate(LocalDateTime.of(2022, 8, 25, 12, 20));
        sprintEntity.setUsers(Set.of(getUserEntity()));
        sprintEntity.setRetrospectives(Set.of(getRetrospectiveEntity()));
        return sprintEntity;
    }

    private static RetrospectiveEntity getRetrospectiveEntity() {
        RetrospectiveEntity retrospectiveEntity = new RetrospectiveEntity();
        retrospectiveEntity.setIdRetrospective(1);
        retrospectiveEntity.setStatus(RetrospectiveStatus.CREATE);
        retrospectiveEntity.setTitle("Retrospective title");
        retrospectiveEntity.setOccurredDate(LocalDateTime.of(2022, 8, 18, 8, 24));
        return retrospectiveEntity;
    }

    private static UserEntity getUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setIdUser(1);
        userEntity.setName("Willian");
        userEntity.setPass("123");
        userEntity.setRole(getRolesEntity());
        return userEntity;
    }

    private static UserDTO getUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setIdUser(1);
        userDTO.setRole("ROLE_MEMBER");
        userDTO.setName("Willian");
        userDTO.setEmail("willian@gmail.com");
        return userDTO;
    }

    private static RolesEntity getRolesEntity() {
        RolesEntity rolesEntity = new RolesEntity();
        rolesEntity.setIdRoles(1);
        rolesEntity.setRoleName("ROLE_MEMBER");
        return rolesEntity;
    }
}
