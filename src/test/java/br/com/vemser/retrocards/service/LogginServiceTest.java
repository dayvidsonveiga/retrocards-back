package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.user.UserLoginDTO;
import br.com.vemser.retrocards.dto.user.UserLoginReturnDTO;
import br.com.vemser.retrocards.entity.RolesEntity;
import br.com.vemser.retrocards.entity.UserEntity;
import br.com.vemser.retrocards.exceptions.NegotiationRulesException;
import br.com.vemser.retrocards.security.TokenService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LogginServiceTest {

    @InjectMocks
    private LogginService logginService;
    @Mock
    private UserService userService;
    @Mock
    private TokenService tokenService;
    @Mock
    private AuthenticationManager authenticationManager;


    @Test
    public void shouldTestLoginWithSuccess() throws NegotiationRulesException {
        UserLoginDTO userLoginDTO = getUserLoginDTO();
        UserEntity userEntity = getUserEntity();
        UserLoginReturnDTO userLoginReturnDTO = getUserLoginReturnDTO();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userEntity,
                        userLoginDTO.getPassword()
                );
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        when(userService.findByEmail(anyString())).thenReturn(userEntity);
        when(userService.checkPasswordIsCorrect(anyString(), anyString())).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(usernamePasswordAuthenticationToken);
        when(userService.login(userLoginDTO, null)).thenReturn(userLoginReturnDTO);

        UserLoginReturnDTO userLoginReturnDTO1 = logginService.login(userLoginDTO);

        assertNotNull(userLoginReturnDTO1);
    }

    @Test(expected = NegotiationRulesException.class)
    public void shouldTestLoginWithoutSuccess() throws NegotiationRulesException {
        UserEntity userEntity = getUserEntity();
        UserLoginDTO userLoginDTO = getUserLoginDTO();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword());
        usernamePasswordAuthenticationToken.setDetails(userEntity);

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        when(userService.findByEmail(anyString())).thenReturn(userEntity);

        logginService.login(userLoginDTO);
    }

    private static UserLoginDTO getUserLoginDTO() {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail("danyllo@gmail.com");
        userLoginDTO.setPassword("123");
        return userLoginDTO;
    }

    private static UserEntity getUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setIdUser(1);
        userEntity.setEmail("danyllo@gmail.com");
        userEntity.setName("Willian");
        userEntity.setPass("123");
        RolesEntity roles = new RolesEntity();
        roles.setRoleName("ROLE_MEMBER");
        userEntity.setRole(roles);
        return userEntity;
    }

    public static UserLoginReturnDTO getUserLoginReturnDTO() {
        UserLoginReturnDTO userLoginReturnDTO = new UserLoginReturnDTO();
        userLoginReturnDTO.setName("danyllo");
        userLoginReturnDTO.setToken("token");
        userLoginReturnDTO.setRole("ROLE_ADMIN");
        return userLoginReturnDTO;
    }
}
