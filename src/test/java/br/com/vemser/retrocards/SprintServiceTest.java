package br.com.vemser.retrocards;

import br.com.vemser.retrocards.dto.sprint.SprintCreateDTO;
import br.com.vemser.retrocards.dto.sprint.SprintDTO;
import br.com.vemser.retrocards.entity.*;
import br.com.vemser.retrocards.enums.KudoStatus;
import br.com.vemser.retrocards.enums.RetrospectiveStatus;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.repository.SprintRepository;
import br.com.vemser.retrocards.service.SprintService;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SprintServiceTest {

    @InjectMocks
    private SprintService sprintService;

    @Mock
    private SprintRepository sprintRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(sprintService, "objectMapper", objectMapper);
    }

    // ---------------------------------------------------------------------------------------
    //                            INÍCIO DOS TESTES PARA create()
    // ---------------------------------------------------------------------------------------

    //TODO CONCLUIR ESTE MÉTODO AMANHÃ!!!!
    @Test
    public void testCreateSprintWithSuccess() throws NegociationRulesException {
        //setup
        SprintEntity sprintEntity = getSprintEntity();
        SprintCreateDTO sprintCreateDTO = getSprintCreateDTO();

        when(sprintRepository.save(any(SprintEntity.class))).thenReturn(sprintEntity);

        //act
        SprintDTO sprintDTO = sprintService.create(sprintCreateDTO);

        //asserts
        assertNotNull(sprintDTO);
        assertEquals(sprintEntity.getTitle(), sprintDTO.getTitle());
        assertEquals(sprintEntity.getStartDate(), sprintDTO.getStartDate());
        assertEquals(sprintEntity.getEndDate(), sprintDTO.getEndDate());
    }

    private static SprintEntity getSprintEntity() {
        SprintEntity sprintEntity = new SprintEntity();
        sprintEntity.setIdSprint(1);
        sprintEntity.setStartDate(LocalDateTime.of(2022, 8, 18, 12, 14));
        sprintEntity.setEndDate(LocalDateTime.of(2022, 8, 25, 12, 20));
        sprintEntity.setTitle("Sprint title");
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

    private static SprintDTO getSprintDTO() {
        SprintDTO sprintDTO = new SprintDTO();
        sprintDTO.setIdSprint(1);
        sprintDTO.setTitle("SprintDTO title");
        sprintDTO.setStartDate(LocalDateTime.of(2022, 8, 18, 8, 30));
        sprintDTO.setEndDate(LocalDateTime.of(2022, 8, 18, 8, 45));
        return sprintDTO;
    }

    private static SprintCreateDTO getSprintCreateDTO() {
        SprintCreateDTO sprintCreateDTO = new SprintCreateDTO();
        sprintCreateDTO.setTitle("SprintCreateDTO title");
        sprintCreateDTO.setStartDate(LocalDate.from(LocalDate.of(2022, 8, 19).atTime(LocalTime.of(8, 45))));
        sprintCreateDTO.setEndDate(LocalDate.from(LocalDate.of(2022, 8, 20).atTime(LocalTime.of(8, 45))));
        return sprintCreateDTO;
    }
}
