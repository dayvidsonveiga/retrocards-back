package br.com.vemser.retrocards.documentation;

import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.dto.user.*;
import br.com.vemser.retrocards.enums.UserType;
import br.com.vemser.retrocards.exceptions.NegotiationRulesException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface UserDocumentation {

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Retorna o usuário criado."),
                    @ApiResponse(responseCode = "403", description = "Permissão inválida! Você não possui permissão de acesso."),
                    @ApiResponse(responseCode = "400", description = "Erro de Requisição! Parâmetro(s) inválido(s)."),
                    @ApiResponse(responseCode = "500", description = "Error! Falha na conexão com os servidores.")
            }
    )
    @PostMapping("/create")
    ResponseEntity<UserDTO> create(@RequestBody @Valid UserCreateDTO userCreateDTO) throws NegotiationRulesException;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Retorna os dados do usuário logado."),
                    @ApiResponse(responseCode = "403", description = "Permissão inválida! Você não possui permissão de acesso."),
                    @ApiResponse(responseCode = "400", description = "Erro de Requisição! Parâmetro(s) inválido(s)."),
                    @ApiResponse(responseCode = "500", description = "Error! Falha na conexão com os servidores.")
            }
    )
    @PostMapping("/login")
    ResponseEntity<UserLoginReturnDTO> login(@RequestBody @Valid UserLoginDTO userLoginDTO) throws NegotiationRulesException;


    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Retorna os dados do usuário e seu cargo atualizado."),
                    @ApiResponse(responseCode = "403", description = "Permissão inválida! Você não possui permissão de acesso."),
                    @ApiResponse(responseCode = "400", description = "Erro de Requisição! Parâmetro(s) inválido(s)."),
                    @ApiResponse(responseCode = "500", description = "Error! Falha na conexão com os servidores.")
            }
    )
    @PutMapping("/change-role/{idUser}")
    ResponseEntity<UserDTO> changeRole(@PathVariable("idUser") Integer idUser,
                                       @RequestParam UserType userType) throws NegotiationRulesException;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Retorna os dados do usuário logado."),
                    @ApiResponse(responseCode = "403", description = "Permissão inválida! Você não possui permissão de acesso."),
                    @ApiResponse(responseCode = "400", description = "Erro de Requisição! Parâmetro(s) inválido(s)."),
                    @ApiResponse(responseCode = "500", description = "Error! Falha na conexão com os servidores.")
            }
    )
    @GetMapping("/get-logged")
    ResponseEntity<UserDTO> getLoggedUser() throws NegotiationRulesException;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Retorna todos os usuários cadastrados no sistema."),
                    @ApiResponse(responseCode = "403", description = "Permissão inválida! Você não possui permissão de acesso."),
                    @ApiResponse(responseCode = "400", description = "Erro de Requisição! Parâmetro(s) inválido(s)."),
                    @ApiResponse(responseCode = "500", description = "Error! Falha na conexão com os servidores.")
            }
    )
    @GetMapping("/list")
    ResponseEntity<PageDTO<UserDTO>> listAll(@RequestParam Integer page,
                                             @RequestParam Integer quantityPerPage) throws NegotiationRulesException;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Retorna todos os usuários do banco com os campos de nome e email."),
                    @ApiResponse(responseCode = "403", description = "Permissão inválida! Você não possui permissão de acesso."),
                    @ApiResponse(responseCode = "400", description = "Erro de Requisição! Parâmetro(s) inválido(s)."),
                    @ApiResponse(responseCode = "500", description = "Error! Falha na conexão com os servidores.")
            }
    )
    @GetMapping("/list-name-email")
    ResponseEntity<List<UserNameEmailDTO>> findUserWithNameAndEmail() throws NegotiationRulesException;
}
