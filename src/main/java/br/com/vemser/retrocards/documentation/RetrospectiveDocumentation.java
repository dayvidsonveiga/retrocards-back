package br.com.vemser.retrocards.documentation;

import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.dto.retrospective.RetrospectiveCreateDTO;
import br.com.vemser.retrocards.dto.retrospective.RetrospectiveDTO;
import br.com.vemser.retrocards.dto.retrospective.RetrospectiveUpdateDTO;
import br.com.vemser.retrocards.dto.retrospective.RetrospectiveWithCountOfItensDTO;
import br.com.vemser.retrocards.enums.RetrospectiveStatus;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface RetrospectiveDocumentation {

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Retorna a retrospectiva criada."),
                    @ApiResponse(responseCode = "403", description = "Permissão Inválida! Você não tem permissão para acessar este serviço."),
                    @ApiResponse(responseCode = "400", description = "Erro de requisição! Parâmetros inválidos."),
                    @ApiResponse(responseCode = "500", description = "Error! Falha na conexão com os servidores.")
            }
    )
    @PostMapping("/create")
    ResponseEntity<RetrospectiveDTO> create(@RequestBody @Valid RetrospectiveCreateDTO retrospectiveCreateDTO) throws NegociationRulesException;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Retorna a retrospectiva atualizada."),
                    @ApiResponse(responseCode = "403", description = "Permissão Inválida! Você não tem permissão para acessar esse serviço."),
                    @ApiResponse(responseCode = "400", description = "Erro de Requisição! Parâmetros inválidos."),
                    @ApiResponse(responseCode = "500", description = "Erro! Falha na conexão com os servidores.")
            }
    )
    @PutMapping("/update/{idRetrospective}")
    ResponseEntity<RetrospectiveDTO> update(@PathVariable("idRetrospective") Integer idRetrospective,
                                            @RequestBody RetrospectiveUpdateDTO retrospectiveUpdateDTO) throws NegociationRulesException;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Retorna a retrospectiva com status atualizado."),
                    @ApiResponse(responseCode = "403", description = "Permissão Inválida! Você não tem permissão para utilizar este serviço."),
                    @ApiResponse(responseCode = "400", description = "Erro de Requisição! Parâmetros inválidos."),
                    @ApiResponse(responseCode = "500", description = "Erro! Falha na conexão com os servidores.")
            }
    )
    @PutMapping("/update-status/{idRetrospective}")
    ResponseEntity<RetrospectiveDTO> updateStatus(@PathVariable("idRetrospective") Integer idRetrospective,
                                                  @RequestParam RetrospectiveStatus status) throws NegociationRulesException;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Retrospectiva deletada com sucesso do banco de dados."),
                    @ApiResponse(responseCode = "403", description = "Permissão Inválida! Você não tem permissão para acessar esse serviço."),
                    @ApiResponse(responseCode = "400", description = "Erro de Requisição! Parâmetros inválidos."),
                    @ApiResponse(responseCode = "500", description = "Erro! Falha na conexão com os servidores.")
            }
    )
    @DeleteMapping("/delete/{idRetrospective}")
    ResponseEntity<Void> delete(@PathVariable("idRetrospective") Integer idRetrospective) throws NegociationRulesException;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Retorna a retrospectiva associada ao ID."),
                    @ApiResponse(responseCode = "403", description = "Permissão Inválida! Você não tem permissão para acessar esse serviço."),
                    @ApiResponse(responseCode = "400", description = "Erro de Requisição! Parâmetros inválidos."),
                    @ApiResponse(responseCode = "500", description = "Erro! Falha na conexão com os servidores.")
            }
    )
    @GetMapping("/list/{idRetrospective}")
    ResponseEntity<RetrospectiveDTO> listById(@PathVariable("idRetrospective") Integer idRetrospective) throws NegociationRulesException;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Retorna todas as retrospectivas associadas ao ID da sprint."),
                    @ApiResponse(responseCode = "403", description = "Permissão Inválida! Você não tem permissão para acessar esse serviço."),
                    @ApiResponse(responseCode = "400", description = "Erro de Requisição! Parâmetros inválidos."),
                    @ApiResponse(responseCode = "500", description = "Error! Falha na conexão com os servidores.")
            }
    )
    @GetMapping("/list/sprint/{idSprint}")
    ResponseEntity<PageDTO<RetrospectiveWithCountOfItensDTO>> listByIdSprint(@PathVariable("idSprint") Integer idSprint,
                                                                             @RequestParam Integer page,
                                                                             @RequestParam Integer quantityPerPage) throws NegociationRulesException;
}
