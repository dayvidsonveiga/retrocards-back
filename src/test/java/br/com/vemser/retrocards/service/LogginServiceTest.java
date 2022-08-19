package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.user.UserLoginDTO;
import br.com.vemser.retrocards.dto.user.UserLoginReturnDTO;
import br.com.vemser.retrocards.entity.RolesEntity;
import br.com.vemser.retrocards.entity.UserEntity;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.repository.UserRepository;
import br.com.vemser.retrocards.security.TokenService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LogginServiceTest {

    @InjectMocks
    private LogginService logginService;

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void shouldTestLoginWithSuccess() throws NegociationRulesException {
        UserEntity userEntity = getUserEntity();
        UserLoginDTO userLoginDTO = getUserLoginDTO();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(1, null);
        usernamePasswordAuthenticationToken.setDetails(userEntity);

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        when(userService.findByEmail(anyString())).thenReturn(userEntity);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
//        when(userService.checkPasswordIsCorrect(anyString(), anyString())).thenReturn(true);

        UserLoginReturnDTO userLoginReturnDTO = logginService.login(userLoginDTO);

        assertNotNull(userLoginReturnDTO);
        assertEquals(userEntity.getName(), userLoginReturnDTO.getName());
    }

    @Test(expected = NegociationRulesException.class)
    public void shouldTestLoginWithoutSuccess() throws NegociationRulesException {
        UserEntity userEntity = getUserEntity();
        UserLoginDTO userLoginDTO = getUserLoginDTO();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userEntity.getPass(), userLoginDTO.getPassword());
        usernamePasswordAuthenticationToken.setDetails(userEntity);

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        when(userService.findByEmail(anyString())).thenReturn(userEntity);
//        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(usernamePasswordAuthenticationToken);

        UserLoginReturnDTO userLoginReturnDTO = logginService.login(userLoginDTO);

        assertNotNull(userLoginReturnDTO);
        assertEquals(userEntity.getName(), userLoginReturnDTO.getName());
    }

    private static UserLoginDTO getUserLoginDTO() {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail("willian@gmail.com");
        userLoginDTO.setPassword("123");
        return userLoginDTO;
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

    private static RolesEntity getRolesEntity() {
        RolesEntity rolesEntity = new RolesEntity();
        rolesEntity.setIdRoles(1);
        rolesEntity.setRoleName("ROLE_MEMBER");
        return rolesEntity;
    }
}
