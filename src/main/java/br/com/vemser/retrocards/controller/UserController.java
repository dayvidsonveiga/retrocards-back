package br.com.vemser.retrocards.controller;

import br.com.vemser.retrocards.dto.user.UserCreateDTO;
import br.com.vemser.retrocards.dto.user.UserDTO;
import br.com.vemser.retrocards.dto.user.UserLoginDTO;
import br.com.vemser.retrocards.dto.user.UserLoginReturnDTO;
import br.com.vemser.retrocards.entity.UserEntity;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.security.TokenService;
import br.com.vemser.retrocards.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;


    @GetMapping("/logged")
    public ResponseEntity<UserDTO> getUsuarioLogado() throws NegociationRulesException {
        return new ResponseEntity<>(userService.getLoggedUser(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginReturnDTO> login(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userLoginDTO.getEmail(),
                        userLoginDTO.getPassword()
                );
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        String token = tokenService.getToken((UserEntity) authentication.getPrincipal());
        return new ResponseEntity<>(userService.returnUserDTOWithToken(userLoginDTO, token), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUserAdmin(@RequestBody @Valid UserCreateDTO userCreateDTO) {
        return new ResponseEntity<>(userService.saveUser(userCreateDTO), HttpStatus.OK);
    }

}
