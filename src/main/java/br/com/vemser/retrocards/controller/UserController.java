package br.com.vemser.retrocards.controller;

import br.com.vemser.retrocards.documentation.UserDocumentation;
import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.dto.user.*;
import br.com.vemser.retrocards.enums.UserType;
import br.com.vemser.retrocards.exceptions.NegotiationRulesException;
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
public class UserController implements UserDocumentation {

    private final UserService userService;
    private final LogginService logginService;


    @Operation(summary = "Criar novo usuário")
    @PostMapping("/create")
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserCreateDTO userCreateDTO) throws NegotiationRulesException {
        return new ResponseEntity<>(userService.create(userCreateDTO), HttpStatus.OK);
    }

    @Operation(summary = "Logar no sistema")
    @PostMapping("/login")
    public ResponseEntity<UserLoginReturnDTO> login(@RequestBody @Valid UserLoginDTO userLoginDTO) throws NegotiationRulesException {
        return new ResponseEntity<>(logginService.login(userLoginDTO), HttpStatus.OK);
    }

    @Operation(summary = "Alterar cargo")
    @PutMapping("/change-role/{idUser}")
    public ResponseEntity<UserDTO> changeRole(@PathVariable("idUser") Integer idUser,
                                              @RequestParam UserType userType) throws NegotiationRulesException {
        return new ResponseEntity<>(userService.changeRole(idUser, userType), HttpStatus.OK);
    }

    @Operation(summary = "Obter dados do usuário logado")
    @GetMapping("/get-logged")
    public ResponseEntity<UserDTO> getLoggedUser() throws NegotiationRulesException {
        return new ResponseEntity<>(userService.getLoggedUser(), HttpStatus.OK);
    }

    @Operation(summary = "Listar todos os usuários cadastrados")
    @GetMapping("/list")
    public ResponseEntity<PageDTO<UserDTO>> listAll(@RequestParam Integer page,
                                                    @RequestParam Integer quantityPerPage) throws NegotiationRulesException {
        return new ResponseEntity<>(userService.listAll(page, quantityPerPage), HttpStatus.OK);
    }

    @Operation(summary = "Listar usuários com os campos de nome e email")
    @GetMapping("/list-name-email")
    public ResponseEntity<List<UserNameEmailDTO>> findUserWithNameAndEmail() throws NegotiationRulesException {
        return new ResponseEntity<>(userService.listUsersWithNameAndEmail(), HttpStatus.OK);
    }
}
