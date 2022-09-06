package br.com.vemser.retrocards.documentation;

import br.com.vemser.retrocards.dto.kudo.kudocard.KudoCardCreateDTO;
import br.com.vemser.retrocards.dto.kudo.kudocard.KudoCardDTO;
import br.com.vemser.retrocards.dto.kudo.kudocard.KudoCardUpdateDTO;
import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.exceptions.NegotiationRulesException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface KudoCardDocumentation {

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Retorna o kudo card criado."),
                    @ApiResponse(responseCode = "403", description = "Permissão Inválida! Você não tem permissão para acessar esse serviço."),
                    @ApiResponse(responseCode = "400", description = "Erro de Requisição! Parâmetros inválidos."),
                    @ApiResponse(responseCode = "500", description = "Erro! Falha na conexão com os servidores.")
            }
    )
    @PostMapping("/create")
    ResponseEntity<KudoCardDTO> create(@RequestBody @Valid KudoCardCreateDTO kudoCardCreateDTO) throws NegotiationRulesException;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Retorna o kudo card atualizado."),
                    @ApiResponse(responseCode = "403", description = "Permissão Inválida! Você não tem permissão para acessar esse serviço."),
                    @ApiResponse(responseCode = "400", description = "Erro de Requisição! Parâmetros inválidos."),
                    @ApiResponse(responseCode = "500", description = "Erro! Falha na conexão com os servidores.")
            }
    )
    @PutMapping("/update/{idKudoCard}")
    ResponseEntity<KudoCardDTO> update(@PathVariable("idKudoCard") Integer idKudoCard,
                                       @RequestBody KudoCardUpdateDTO kudoCardUpdateDTO) throws NegotiationRulesException;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Kudo card deletado do banco de dados."),
                    @ApiResponse(responseCode = "403", description = "Permissão Inválida! Você não tem permissão para acessar esse serviço."),
                    @ApiResponse(responseCode = "400", description = "Erro de Requisição! Parâmetros inválidos."),
                    @ApiResponse(responseCode = "500", description = "Erro! Falha na conexão com os servidores.")
            }
    )
    @DeleteMapping("/delete/{idKudoCard}")
    ResponseEntity<Void> delete(@PathVariable("idKudoCard") Integer idKudoCard) throws NegotiationRulesException;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Retorna lista de kudo cards associados a Kudo Box."),
                    @ApiResponse(responseCode = "403", description = "Permissão Inválida! Você não tem permissão para acessar esse serviço."),
                    @ApiResponse(responseCode = "400", description = "Erro de Requisição! Parâmetros inválidos."),
                    @ApiResponse(responseCode = "500", description = "Erro! Falha na conexão com os servidores.")
            }
    )
    @GetMapping("/list/kudocards/{idKudoBox}")
    ResponseEntity<PageDTO<KudoCardDTO>> listKudoCardByIdKudoBox(@PathVariable("idKudoBox") Integer idKudoBox,
                                                                 @RequestParam Integer page,
                                                                 @RequestParam Integer quantityPerPage) throws NegotiationRulesException;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Retorna kudo card associado ao ID."),
                    @ApiResponse(responseCode = "403", description = "Permissão Inválida! Você não tem permissão para acessar esse serviço."),
                    @ApiResponse(responseCode = "400", description = "Erro de Requisição! Parâmetros inválidos."),
                    @ApiResponse(responseCode = "500", description = "Erro! Falha na conexão com os servidores.")
            }
    )
    @GetMapping("/list/{idKudoCard}")
    ResponseEntity<KudoCardDTO> listById(@PathVariable("idKudoCard") Integer idKudoCard) throws NegotiationRulesException;
}
