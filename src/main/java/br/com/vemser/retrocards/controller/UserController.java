package br.com.vemser.retrocards.controller;

import br.com.vemser.retrocards.documentation.UserDocumentation;
import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.dto.user.*;
import br.com.vemser.retrocards.entity.UserEntity;
import br.com.vemser.retrocards.enums.UserType;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.security.TokenService;
import br.com.vemser.retrocards.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
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
public class UserController implements UserDocumentation {

    private final UserService userService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;


    @Operation(summary = "Register new user")
    @PostMapping("/create")
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserCreateDTO userCreateDTO) throws NegociationRulesException {
        return new ResponseEntity<>(userService.create(userCreateDTO), HttpStatus.OK);
    }

    @Operation(summary = "Log in")
    @PostMapping("/login")
    public ResponseEntity<UserLoginReturnDTO> login(@RequestBody @Valid UserLoginDTO userLoginDTO) throws NegociationRulesException {
        UserEntity userEntity = userService.findByEmail(userLoginDTO.getEmail());
        if (userService.checkPasswordIsCorrect(userLoginDTO.getPassword(), userEntity.getPassword())) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userLoginDTO.getEmail(),
                            userLoginDTO.getPassword()
                    );
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            String token = tokenService.getToken((UserEntity) authentication.getPrincipal());
            return new ResponseEntity<>(userService.login(userLoginDTO, token), HttpStatus.OK);
        } else {
            throw new NegociationRulesException("Email or password incorrect");
        }
    }

    @Operation(summary = "Change role")
    @PutMapping("/change-role/{idUser}")
    public ResponseEntity<Void> changeRole(@PathVariable("idUser") Integer idUser, UserType userType) throws NegociationRulesException {
        userService.changeRole(idUser, userType);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Get data from the logged on user")
    @GetMapping("/get-logged")
    public ResponseEntity<UserDTO> getLoggedUser() throws NegociationRulesException {
        return new ResponseEntity<>(userService.getLoggedUser(), HttpStatus.OK);
    }

    @Operation(summary = "List all the registered users")
    @GetMapping("/list")
    public ResponseEntity<PageDTO<UserDTO>> listAll(Integer pagina, Integer registros) throws NegociationRulesException {
        return new ResponseEntity<>(userService.listAll(pagina, registros), HttpStatus.OK);
    }

    @Operation(summary = "List user by id with name and email")
    @GetMapping("/list-name-email/{idUser}")
    public ResponseEntity<UserNomeEmailDTO> findUserWithNameAndEmail(@PathVariable("idUser") Integer idUser) throws NegociationRulesException {
        return new ResponseEntity<>(userService.findUserWithNameAndEmail(idUser), HttpStatus.OK);
    }
}
