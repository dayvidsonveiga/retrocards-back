package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.dto.user.*;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

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
        UserDTO userDTO = userService.changeRole(1, UserType.MEMBER);

        assertNotNull(userDTO);
    }

    @Test
    public void shouldTestLoginWithSuccess() throws NegociationRulesException {
        UserEntity userEntity = getUserEntity();
        UserLoginDTO userLoginDTO = getUserLoginDTO();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userEntity));

        UserLoginReturnDTO userLogin = userService.login(userLoginDTO, "string");

        assertNotNull(userLogin);
    }

    @Test
    public void shouldTestGetIdLoggedUserWithSuccess() throws NegociationRulesException {
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

    @Test(expected = NegociationRulesException.class)
    public void shouldTestGetIdLoggedUserWithoutSuccess() throws NegociationRulesException {
        UserEntity userLoginEntity = getUserEntity();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        "anonymousUser",
                        null
                );

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        userService.getLoggedUser();
    }

    @Test
    public void shouldTestCheckEmailExistWithSuccess() throws NegociationRulesException {
        UserEntity userEntity = getUserEntity();
        userEntity.setEmail("dayvidson@gmail.com");
    }

    @Test(expected = NegociationRulesException.class)
    public void shouldTestCheckEmailExistWithoutSuccess() throws NegociationRulesException {
        UserEntity userEntity = getUserEntity();
        userEntity.setEmail("dayvidson@gmail.com");

        UserEntity userEntity2 = getUserEntity();
        userEntity2.setEmail("dayvidson@gmail.com");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userEntity));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userEntity2));

        userService.checkEmailExist(userEntity.getEmail());
    }

    @Test
    public void shouldTestCheckPasswordIsCorrectTrue() {
        String pass1 = "teste";
        String pass2 = "teste";
        Boolean verify = true;

        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(verify);

        Boolean test = userService.checkPasswordIsCorrect(pass1, pass2);

        assertEquals(test, true);
    }

    @Test
    public void shouldTestCheckPasswordIsCorrectFalse() {
        String pass1 = "teste";
        String pass2 = "string";
        Boolean verify = false;

        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(verify);

        Boolean test = userService.checkPasswordIsCorrect(pass1, pass2);

        assertEquals(test, false);
    }

    @Test
    public void shouldTestListAllWithSuccess() throws NegociationRulesException {
        Integer pageNumber = 0;
        Integer registerNumber = 10;
        List<UserEntity> listUsers = List.of(getUserEntity());
        Page<UserEntity> dtoPage = new PageImpl<>(listUsers);

        when(userRepository.findAll(any(Pageable.class))).thenReturn(dtoPage);

        PageDTO<UserDTO> userDTO = userService.listAll(pageNumber, registerNumber);

        assertNotNull(userDTO);
        assertEquals(1, userDTO.getTotalElements().intValue());
        assertEquals(1, userDTO.getTotalPages().intValue());
    }

    @Test(expected = NegociationRulesException.class)
    public void shouldTestListAllWithoutSuccess() throws NegociationRulesException {
        Integer pageNumber = 0;
        Integer registerNumber = 10;
        List<UserEntity> listUsers = new ArrayList<>();
        Page<UserEntity> dtoPage = new PageImpl<>(listUsers);

        when(userRepository.findAll(any(Pageable.class))).thenReturn(dtoPage);

        userService.listAll(pageNumber, registerNumber);
    }

    @Test
    public void shouldTestListUsersWithNameAndEmailWithSuccess() throws NegociationRulesException {
        List<UserEntity> listUsers = List.of(getUserEntity());

        when(userRepository.findAll()).thenReturn(listUsers);

        List<UserNameEmailDTO> listUserNameEmail = userService.listUsersWithNameAndEmail();

        assertNotNull(listUserNameEmail);
        assertEquals(listUsers.size(), listUserNameEmail.size());
    }

    @Test(expected = NegociationRulesException.class)
    public void shouldTestListUsersWithNameAndEmailWithoutSuccess() throws NegociationRulesException {
        List<UserEntity> listUsers = new ArrayList<>();

        when(userRepository.findAll()).thenReturn(listUsers);

        userService.listUsersWithNameAndEmail();
    }

    @Test
    public void shouldTestGetLoggedUser() throws NegociationRulesException {
        //setup
        UserEntity userEntity = getUserEntity();
        shouldTestGetIdLoggedUserWithSuccess();

        when(userRepository.findById(anyInt())).thenReturn(Optional.of(userEntity));

        //act
        UserDTO userDTO = userService.getLoggedUser();

        //asserts
        assertNotNull(userDTO);
        assertEquals(userEntity.getIdUser(), userDTO.getIdUser());
        assertEquals(userEntity.getName(), userDTO.getName());
        assertEquals(userEntity.getEmail(), userDTO.getEmail());
        assertEquals(userEntity.getRole().getRoleName(), userDTO.getRole());
    }

    private static UserEntity getUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setIdUser(1);
        userEntity.setEmail("willian@gmail.com");
        userEntity.setName("Willian");
        userEntity.setPass("123");
        userEntity.setRole(getRolesEntity());
        return userEntity;
    }

    private static UserCreateDTO getUserCreateDTO() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setName("Willian");
        userCreateDTO.setEmail("willian@gmail.com");
        userCreateDTO.setPassword("123");
        return userCreateDTO;
    }

    private static UserLoginDTO getUserLoginDTO() {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail("dayvidson@gmail.com");
        userLoginDTO.setPassword("123");
        return userLoginDTO;
    }

    private static RolesEntity getRolesEntity() {
        RolesEntity rolesEntity = new RolesEntity();
        rolesEntity.setIdRoles(1);
        rolesEntity.setRoleName("ROLE_MEMBER");
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
