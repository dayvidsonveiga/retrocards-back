package br.com.vemser.retrocards.controller;

import br.com.vemser.retrocards.documentation.UserDocumentation;
import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.dto.user.*;
import br.com.vemser.retrocards.enums.UserType;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.service.LogginService;
import br.com.vemser.retrocards.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;
    private final LogginService logginService;


    @Operation(summary = "Register new user")
    @PostMapping("/create")
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserCreateDTO userCreateDTO) throws NegociationRulesException {
        return new ResponseEntity<>(userService.create(userCreateDTO), HttpStatus.OK);
    }

    @Operation(summary = "Log in")
    @PostMapping("/login")
    public ResponseEntity<UserLoginReturnDTO> login(@RequestBody @Valid UserLoginDTO userLoginDTO) throws NegociationRulesException {
        return new ResponseEntity<>(logginService.login(userLoginDTO), HttpStatus.OK);
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
    @GetMapping("/list-name-email")
    public ResponseEntity<List<UserNameEmailDTO>> findUserWithNameAndEmail() throws NegociationRulesException {
        return new ResponseEntity<>(userService.listUsersWithNameAndEmail(), HttpStatus.OK);
    }
}
