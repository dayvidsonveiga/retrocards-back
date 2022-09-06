package br.com.vemser.retrocards.documentation;

import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxCreateDTO;
import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxDTO;
import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxUpdateDTO;
import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxWithCountOfItensDTO;
import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.exceptions.NegotiationRulesException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface KudoBoxDocumentation {

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Retorna o kudo box criada."),
                    @ApiResponse(responseCode = "403", description = "Permissão Inválida! Você não tem permissão para acessar esse serviço."),
                    @ApiResponse(responseCode = "400", description = "Erro de Requisição! Parâmetros inválidos."),
                    @ApiResponse(responseCode = "500", description = "Erro! Falha na conexão com os servidores.")
            }
    )
    @PostMapping("/create")
    ResponseEntity<KudoBoxDTO> create(@RequestBody @Valid KudoBoxCreateDTO kudoBoxCreateDTO) throws NegotiationRulesException;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Retorna o kudo box atualizado."),
                    @ApiResponse(responseCode = "403", description = "Permissão Inválida! Você não tem permissão para acessar esse serviço."),
                    @ApiResponse(responseCode = "400", description = "Erro de Requisição! Parâmetros inválidos."),
                    @ApiResponse(responseCode = "500", description = "Erro! Falha na conexão com os servidores.")
            }
    )
    @PutMapping("/update/{idKudobox}")
    ResponseEntity<KudoBoxDTO> update(@PathVariable("idKudobox") Integer idKudobox,
                                      @RequestBody KudoBoxUpdateDTO kudoBoxUpdateDTO) throws NegotiationRulesException;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Kudo box deletado do banco de dados."),
                    @ApiResponse(responseCode = "403", description = "Permissão Inválida! Você não tem permissão para acessar esse serviço."),
                    @ApiResponse(responseCode = "400", description = "Erro de Requisição! Parâmetros inválidos."),
                    @ApiResponse(responseCode = "500", description = "Erro! Falha na conexão com os servidores.")
            }
    )
    @DeleteMapping("/delete/{idKudoBox}")
    ResponseEntity<Void> delete(@PathVariable("idKudoBox") Integer idKudoBox) throws NegotiationRulesException;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Retorna lista de kudo boxes associados a Sprint."),
                    @ApiResponse(responseCode = "403", description = "Permissão Inválida! Você não tem permissão para acessar esse serviço."),
                    @ApiResponse(responseCode = "400", description = "Erro de Requisição! Parâmetros inválidos."),
                    @ApiResponse(responseCode = "500", description = "Erro! Falha na conexão com os servidores.")
            }
    )
    @GetMapping("/list/sprint/{idSprint}")
    ResponseEntity<PageDTO<KudoBoxWithCountOfItensDTO>> listKudoBoxByIdSprint(@PathVariable("idSprint") Integer idSprint,
                                                                              @RequestParam Integer page,
                                                                              @RequestParam Integer quantityPerPage) throws NegotiationRulesException;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Retorna kudo box associado ao ID."),
                    @ApiResponse(responseCode = "403", description = "Permissão Inválida! Você não tem permissão para acessar esse serviço."),
                    @ApiResponse(responseCode = "400", description = "Erro de Requisição! Parâmetros inválidos."),
                    @ApiResponse(responseCode = "500", description = "Erro! Falha na conexão com os servidores.")
            }
    )
    @GetMapping("/list/{idKudobox}")
    ResponseEntity<KudoBoxDTO> listById(@PathVariable("idKudobox") Integer idRetrospective) throws NegotiationRulesException;
}
