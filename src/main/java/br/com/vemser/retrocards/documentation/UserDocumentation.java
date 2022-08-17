package br.com.vemser.retrocards.documentation;

import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.dto.user.*;
import br.com.vemser.retrocards.enums.UserType;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface UserDocumentation {

    @Operation(summary = "Register new user")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucess! Returns the successfully created user."),
                    @ApiResponse(responseCode = "403", description = "Invalid Permission! You do not have permission to acesses."),
                    @ApiResponse(responseCode = "400", description = "Bad Request! Invalid parameters"),
                    @ApiResponse(responseCode = "500", description = "Error! Could not connect to the server.")
            }
    )
    @PostMapping("/create")
    ResponseEntity<UserDTO> create(@RequestBody @Valid UserCreateDTO userCreateDTO) throws NegociationRulesException;

    @Operation(summary = "Change role")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucess! Returns the successfully created user."),
                    @ApiResponse(responseCode = "403", description = "Invalid Permission! You do not have permission to acesses."),
                    @ApiResponse(responseCode = "400", description = "Bad Request! Invalid parameters"),
                    @ApiResponse(responseCode = "500", description = "Error! Could not connect to the server.")
            }
    )
    @PutMapping("/change-role/{idUser}")
    ResponseEntity<Void> changeRole(@PathVariable("idUser") Integer idUser, UserType userType) throws NegociationRulesException;

    @Operation(summary = "Log in")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucess! Returns the successfully log in token."),
                    @ApiResponse(responseCode = "403", description = "Invalid Permission! You do not have permission to acesses."),
                    @ApiResponse(responseCode = "400", description = "Bad Request! Invalid parameters"),
                    @ApiResponse(responseCode = "500", description = "Error! Could not connect to the server.")
            }
    )
    @PostMapping("/login")
    ResponseEntity<UserLoginReturnDTO> login(@RequestBody @Valid UserLoginDTO userLoginDTO) throws NegociationRulesException;

    @Operation(summary = "Get data from the logged on user")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucess! Returns the logged user."),
                    @ApiResponse(responseCode = "403", description = "Invalid Permission! You do not have permission to acesses."),
                    @ApiResponse(responseCode = "400", description = "Bad Request! Invalid parameters"),
                    @ApiResponse(responseCode = "500", description = "Error! Could not connect to the server.")
            }
    )
    @GetMapping("/get-logged")
    ResponseEntity<UserDTO> getLoggedUser() throws NegociationRulesException;

    @Operation(summary = "List all the registered users")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucess! Returns the list of all users."),
                    @ApiResponse(responseCode = "403", description = "Invalid Permission! You do not have permission to acesses."),
                    @ApiResponse(responseCode = "400", description = "Bad Request! Invalid parameters"),
                    @ApiResponse(responseCode = "500", description = "Error! Could not connect to the server.")
            }
    )
    @GetMapping("/list")
    ResponseEntity<PageDTO<UserDTO>> listAll(Integer pagina, Integer registros) throws NegociationRulesException;

    @Operation(summary = "List user by id with name and email")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucess! Returns the list of all users."),
                    @ApiResponse(responseCode = "403", description = "Invalid Permission! You do not have permission to acesses."),
                    @ApiResponse(responseCode = "400", description = "Bad Request! Invalid parameters"),
                    @ApiResponse(responseCode = "500", description = "Error! Could not connect to the server.")
            }
    )
    @GetMapping("/list-name-email/{idUser}")
    ResponseEntity<UserNameEmailDTO> findUserWithNameAndEmail(@PathVariable("idUser") Integer idUser) throws NegociationRulesException;
}
