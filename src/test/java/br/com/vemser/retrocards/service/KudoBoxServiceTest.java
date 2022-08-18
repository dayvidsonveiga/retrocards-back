package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxCreateDTO;
import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxDTO;
import br.com.vemser.retrocards.dto.sprint.SprintCreateDTO;
import br.com.vemser.retrocards.entity.RetrospectiveEntity;
import br.com.vemser.retrocards.entity.RolesEntity;
import br.com.vemser.retrocards.entity.SprintEntity;
import br.com.vemser.retrocards.entity.UserEntity;
import br.com.vemser.retrocards.enums.KudoStatus;
import br.com.vemser.retrocards.enums.RetrospectiveStatus;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.repository.KudoBoxRepository;
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
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class KudoBoxServiceTest {

    @InjectMocks
    private KudoBoxService kudoBoxService;

    @Mock
    private KudoBoxRepository kudoBoxRepository;

    @Mock
    private SprintRepository sprintRepository;

    @Mock
    private SprintService sprintService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(kudoBoxService, "objectMapper", objectMapper);
    }

    //TODO COMEÇAR DAQUI QUANDO RETORNAR
    @Test
    public void sholdTestCreateKudoBoxWithSuccess() throws NegociationRulesException {
        KudoBoxCreateDTO kudoBoxCreateDTO = getKudoBoxCreateDTO();
        SprintEntity sprintEntity = getSprintEntity();

        when(sprintRepository.findById(anyInt())).thenReturn(Optional.of(sprintEntity));
    }

    private static KudoBoxCreateDTO getKudoBoxCreateDTO() {
        KudoBoxCreateDTO kudoBoxCreateDTO = new KudoBoxCreateDTO();
        kudoBoxCreateDTO.setIdSprint(1);
        kudoBoxCreateDTO.setStatus(KudoStatus.CREATE.getStatus());
        kudoBoxCreateDTO.setTitle("Kudo box title");
        kudoBoxCreateDTO.setEndDate(LocalDate.from(LocalDate.of(2022, 8, 25).atTime(12, 54)));
        return kudoBoxCreateDTO;
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
}
