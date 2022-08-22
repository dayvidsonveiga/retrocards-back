package br.com.vemser.retrocards.documentation;

import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.dto.sprint.*;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface SprintDocumentation {

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Retorna a sprint criada."),
                    @ApiResponse(responseCode = "403", description = "Permissão Inválida! Você não tem permissão para acessar esse serviço."),
                    @ApiResponse(responseCode = "400", description = "Erro de Requisição! Parâmetros inválidos."),
                    @ApiResponse(responseCode = "500", description = "Erro! Falha na conexão com os servidores.")
            }
    )
    @PostMapping("/create")
    ResponseEntity<SprintDTO> create(@RequestBody @Valid SprintCreateDTO sprintCreateDTO) throws NegociationRulesException;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Retorna a sprint atualizada."),
                    @ApiResponse(responseCode = "403", description = "Permissão Inválida! Você não tem permissão para utilizar este serviço."),
                    @ApiResponse(responseCode = "400", description = "Erro de Requisição! Parâmetros inválidos."),
                    @ApiResponse(responseCode = "500", description = "Erro! Falha na conexão com os servidores.")
            }
    )
    @PutMapping("/update/{idSprint}")
    ResponseEntity<SprintDTO> update(@PathVariable("idSprint") Integer idSprint,
                                     @RequestBody @Valid SprintUpdateDTO sprintUpdateDTO) throws NegociationRulesException;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Sprint deletada com sucesso do banco de dados."),
                    @ApiResponse(responseCode = "403", description = "Permissão Inválida! Você não tem permissão para acessar esse serviço."),
                    @ApiResponse(responseCode = "400", description = "Erro de Requisição! Parâmetros inválidos."),
                    @ApiResponse(responseCode = "500", description = "Erro! Falha na conexão com os servidores.")
            }
    )
    @DeleteMapping("/delete/{idSprint}")
    ResponseEntity<Void> delete(@PathVariable("idSprint") Integer idSprint) throws NegociationRulesException;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Retorna a lista de sprints ordenadas pela ordem de conclusão."),
                    @ApiResponse(responseCode = "403", description = "Permissão Inválida! Você não tem permissão para acessar esse serviço."),
                    @ApiResponse(responseCode = "400", description = "Erro de Requisição! Parâmetros inválidos."),
                    @ApiResponse(responseCode = "500", description = "Erro! Falha na conexão com os servidores.")
            }
    )
    @GetMapping("/list")
    ResponseEntity<PageDTO<SprintWithEndDateDTO>> listByDateDesc(@RequestParam Integer page,
                                                                 @RequestParam Integer quantityPerPage) throws NegociationRulesException;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Retorna a sprint com a checkagem de Retrospectivas e Kudo boxes em progresso"),
                    @ApiResponse(responseCode = "403", description = "Permissão Inválida! Você não tem permissão para acessar esse serviço."),
                    @ApiResponse(responseCode = "400", description = "Erro de Requisição! Parâmetros inválidos."),
                    @ApiResponse(responseCode = "500", description = "Erro! Falha na conexão com os servidores.")
            }
    )
    @GetMapping("/check-progress/{idSprint}")
    ResponseEntity<SprintCheckDTO> checkProgressRetrospectiveAndKudobox(@PathVariable("idSprint") Integer idSprint) throws NegociationRulesException;
}
