package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.user.UserCreateDTO;
import br.com.vemser.retrocards.dto.user.UserDTO;
import br.com.vemser.retrocards.entity.RolesEntity;
import br.com.vemser.retrocards.entity.UserEntity;
import br.com.vemser.retrocards.enums.UserType;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.repository.RolesRepository;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import javax.management.relation.Role;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RolesService rolesService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RolesRepository rolesRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(userService, "objectMapper", objectMapper);
    }

    @Test
    public void shouldTestCreateWithSuccess() throws NegociationRulesException {
        UserCreateDTO userCreateDTO = getUserCreateDTO();
        UserEntity userEntity = getUserEntity();

        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDTO userDTO = userService.create(userCreateDTO);

        assertNotNull(userDTO);
        assertEquals(userEntity.getIdUser(), userDTO.getIdUser());
        assertEquals(userEntity.getName(), userDTO.getName());
        assertEquals(userEntity.getEmail(), userDTO.getEmail());
        assertEquals(userEntity.getRole().getRoleName(), userDTO.getRole());
    }

    @Test
    public void shouldTestChangeRoleWithSucess() throws NegociationRulesException {
        UserEntity userLoginEntity = getUserEntity();
        userLoginEntity.getRole().setRoleName(UserType.ADMIN.getRoleName());
        RolesEntity roles = getRolesEntity();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        1,
                        null
                );

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        when(userRepository.findById(anyInt())).thenReturn(Optional.of(userLoginEntity));
        when(rolesService.findByRoleName(anyString())).thenReturn(roles);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userLoginEntity);

        UserDTO userDTO = userService.changeRole(1, UserType.MEMBER);

        assertNotNull(userDTO);
    }

    @Test(expected = NegociationRulesException.class)
    public void shouldTestChangeRoleWithoutSucess() throws NegociationRulesException {
        UserEntity userLoginEntity = getUserEntity();
        userLoginEntity.getRole().setRoleName(UserType.MEMBER.getRoleName());
        RolesEntity roles = getRolesEntity();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        1,
                        null
                );

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        when(userRepository.findById(anyInt())).thenReturn(Optional.of(userLoginEntity));
        when(rolesService.findByRoleName(anyString())).thenReturn(roles);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userLoginEntity);

        UserDTO userDTO = userService.changeRole(1, UserType.MEMBER);

        assertNotNull(userDTO);
    }

    @Test
    public void shouldTestGetIdLoggedUser() throws NegociationRulesException {
        UserEntity userLoginEntity = getUserEntity();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        1,
                        null
                );

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        when(userRepository.findById(anyInt())).thenReturn(Optional.of(userLoginEntity));

        UserDTO userLogin = userService.getLoggedUser();

        assertNotNull(userLogin);
        assertEquals(1, userLogin.getIdUser().intValue());
        assertEquals("Willian", userLogin.getName());
    }

    private static UserEntity getUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setIdUser(1);
        userEntity.setName("Willian");
        userEntity.setPass("123");
        RolesEntity roles = new RolesEntity();
        roles.setRoleName("ROLE_ADMIN");
        userEntity.setRole(roles);
        return userEntity;
    }

    private static UserCreateDTO getUserCreateDTO() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setName("Willian");
        userCreateDTO.setEmail("willian@gmail.com");
        userCreateDTO.setPassword("123");
        return userCreateDTO;
    }

    private static RolesEntity getRolesEntity() {
        RolesEntity rolesEntity = new RolesEntity();
        rolesEntity.setIdRoles(1);
        rolesEntity.setRoleName("ROLE_ADMIN");
        return rolesEntity;
    }

    private static UserDTO getUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setIdUser(1);
        userDTO.setRole("ROLE_ADMIN");
        userDTO.setName("Willian");
        userDTO.setEmail("willian@gmail.com");
        return userDTO;
    }
}
