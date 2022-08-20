package br.com.vemser.retrocards.documentation;

import br.com.vemser.retrocards.dto.kudo.kudocard.KudoCardCreateDTO;
import br.com.vemser.retrocards.dto.kudo.kudocard.KudoCardDTO;
import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface KudoCardDocumentation {

    @Operation(summary = "Register new kudocard")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucess! Returns the successfully created kudocard."),
                    @ApiResponse(responseCode = "403", description = "Invalid Permission! You do not have permission to acesses."),
                    @ApiResponse(responseCode = "400", description = "Bad Request! Invalid parameters"),
                    @ApiResponse(responseCode = "500", description = "Error! Could not connect to the server.")
            }
    )
    @PostMapping("/create")
    ResponseEntity<KudoCardDTO> create(@RequestBody @Valid KudoCardCreateDTO kudoCardCreateDTO) throws NegociationRulesException;

    @Operation(summary = "Remove retrospective item")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucess! Returns the successfully created kudocard."),
                    @ApiResponse(responseCode = "403", description = "Invalid Permission! You do not have permission to acesses."),
                    @ApiResponse(responseCode = "400", description = "Bad Request! Invalid parameters"),
                    @ApiResponse(responseCode = "500", description = "Error! Could not connect to the server.")
            }
    )
    @PostMapping("/delete/{idKudocard}")
    ResponseEntity<Void> delete(@PathVariable("idKudocard") Integer idKudocard) throws NegociationRulesException;

    @Operation(summary = "List all the kudo cards associated with the kudo box")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucess! Returns all kudocards referring to ID kudoboxe."),
                    @ApiResponse(responseCode = "403", description = "Invalid Permission! You do not have permission to acesses."),
                    @ApiResponse(responseCode = "400", description = "Bad Request! Invalid parameters"),
                    @ApiResponse(responseCode = "500", description = "Error! Could not connect to the server.")
            }
    )
    @GetMapping("/list/kudobox/{idKudoBox}")
    ResponseEntity<PageDTO<KudoCardDTO>> listKudoCardByIdKudoBox(@PathVariable("idKudoBox") Integer idKudoBox, Integer pagina, Integer registros) throws NegociationRulesException;

    @Operation(summary = "List all the kudo cards ordered by start date ascending")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucess! Returns all kudocards referring to ID kudoboxe."),
                    @ApiResponse(responseCode = "403", description = "Invalid Permission! You do not have permission to acesses."),
                    @ApiResponse(responseCode = "400", description = "Bad Request! Invalid parameters"),
                    @ApiResponse(responseCode = "500", description = "Error! Could not connect to the server.")
            }
    )
    @GetMapping("/list/start-date")
    ResponseEntity<PageDTO<KudoCardDTO>> listKudoCardsByStartDate(Integer pagina, Integer registros) throws NegociationRulesException;
}
