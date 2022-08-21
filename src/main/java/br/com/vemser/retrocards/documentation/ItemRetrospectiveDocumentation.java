package br.com.vemser.retrocards.documentation;

import br.com.vemser.retrocards.dto.ItemRetrospective.ItemRetrospectiveCreateDTO;
import br.com.vemser.retrocards.dto.ItemRetrospective.ItemRetrospectiveDTO;
import br.com.vemser.retrocards.dto.ItemRetrospective.ItemRetrospectiveUpdateDTO;
import br.com.vemser.retrocards.enums.ItemType;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface ItemRetrospectiveDocumentation {

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Retorna o item de retrospectiva criado."),
                    @ApiResponse(responseCode = "403", description = "Permissão Inválida! Você não tem permissão para acessar esse serviço."),
                    @ApiResponse(responseCode = "400", description = "Erro de Requisição! Parâmetros inválidos."),
                    @ApiResponse(responseCode = "500", description = "Erro! Falha na conexão com os servidores.")
            }
    )
    @PostMapping("/create")
    ResponseEntity<ItemRetrospectiveDTO> create(@RequestBody @Valid ItemRetrospectiveCreateDTO itemRetrospectiveCreateDTO,
                                                @RequestParam ItemType itemType) throws NegociationRulesException;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Retorna o item de retrospectiva atualizado."),
                    @ApiResponse(responseCode = "403", description = "Permissão Inválida! Você não tem permissão para acessar esse serviço."),
                    @ApiResponse(responseCode = "400", description = "Erro de Requisição! Parâmetros inválidos."),
                    @ApiResponse(responseCode = "500", description = "Erro! Falha na conexão com os servidores.")
            }
    )
    @PutMapping("/update/{idItem}")
    ResponseEntity<ItemRetrospectiveDTO> update(@PathVariable("idItem") Integer idItem,
                                                @RequestParam ItemType itemType,
                                                @RequestBody ItemRetrospectiveUpdateDTO itemRetrospectiveUpdateDTO) throws NegociationRulesException;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Item de retrospectiva deletado do banco de dados."),
                    @ApiResponse(responseCode = "403", description = "Permissão Inválida! Você não tem permissão para acessar esse serviço."),
                    @ApiResponse(responseCode = "400", description = "Erro de Requisição! Parâmetros inválidos."),
                    @ApiResponse(responseCode = "500", description = "Erro! Falha na conexão com os servidores.")
            }
    )
    @DeleteMapping("/delete/{idItem}")
    void delete(@PathVariable("idItem") Integer idItem) throws NegociationRulesException;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Retorna lista de itens de retrospectiva associados a Retrospectiva."),
                    @ApiResponse(responseCode = "403", description = "Permissão Inválida! Você não tem permissão para acessar esse serviço."),
                    @ApiResponse(responseCode = "400", description = "Erro de Requisição! Parâmetros inválidos."),
                    @ApiResponse(responseCode = "500", description = "Erro! Falha na conexão com os servidores.")
            }
    )
    @GetMapping("/list/retrospective/{idRetrospective}")
    ResponseEntity<List<ItemRetrospectiveDTO>> listByIdRetrospective(@PathVariable("idRetrospective") Integer idRetrospective) throws NegociationRulesException;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Retorna item de retrospectiva associado ao ID."),
                    @ApiResponse(responseCode = "403", description = "Permissão Inválida! Você não tem permissão para acessar esse serviço."),
                    @ApiResponse(responseCode = "400", description = "Erro de Requisição! Parâmetros inválidos."),
                    @ApiResponse(responseCode = "500", description = "Erro! Falha na conexão com os servidores.")
            }
    )
    @GetMapping("/list/{idItemRetrospective}")
    ResponseEntity<ItemRetrospectiveDTO> listById(@PathVariable("idItemRetrospective") Integer idItemRetrospective) throws NegociationRulesException;
}
