package br.com.vemser.retrocards.documentation;

import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxCreateDTO;
import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxDTO;
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
import java.util.List;

public interface KudoBoxDocumentation {

    @Operation(summary = "Register new kudobox")
    @PostMapping("/create")
    ResponseEntity<KudoBoxDTO> create(@RequestBody @Valid KudoBoxCreateDTO kudoBoxCreateDTO) throws NegociationRulesException;

    @Operation(summary = "List all the kudo boxes associated with a sprint")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucess! Returns all kudoboxes referring to ID sprint."),
                    @ApiResponse(responseCode = "403", description = "Invalid Permission! You do not have permission to acesses."),
                    @ApiResponse(responseCode = "400", description = "Bad Request! Invalid parameters"),
                    @ApiResponse(responseCode = "500", description = "Error! Could not connect to the server.")
            }
    )
    @GetMapping("/list/sprint/{idSprint}")
    ResponseEntity<PageDTO<KudoBoxDTO>> listKudoBoxByIdSprint(@PathVariable("idSprint") Integer idSprint, Integer pagina, Integer registros) throws NegociationRulesException;
}
