package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.user.UserLoginDTO;
import br.com.vemser.retrocards.dto.user.UserLoginReturnDTO;
import br.com.vemser.retrocards.entity.UserEntity;
import br.com.vemser.retrocards.exceptions.NegotiationRulesException;
import br.com.vemser.retrocards.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogginService {

    private final UserService userService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public UserLoginReturnDTO login(UserLoginDTO userLoginDTO) throws NegotiationRulesException {
        UserEntity userEntity = userService.findByEmail(userLoginDTO.getEmail());
        if (userService.checkPasswordIsCorrect(userLoginDTO.getPassword(), userEntity.getPassword())) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userLoginDTO.getEmail(),
                            userLoginDTO.getPassword()
                    );
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            String token = tokenService.getToken((UserEntity) authentication.getPrincipal());
            return userService.login(userLoginDTO, token);
        } else {
            throw new NegotiationRulesException("Email ou senha incorreta!");
        }
    }
}
