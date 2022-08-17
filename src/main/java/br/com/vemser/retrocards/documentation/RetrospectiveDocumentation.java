package br.com.vemser.retrocards.documentation;

import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.dto.retrospective.Retrospective.RetrospectiveCreateDTO;
import br.com.vemser.retrocards.dto.retrospective.Retrospective.RetrospectiveDTO;
import br.com.vemser.retrocards.enums.RetrospectiveStatus;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface RetrospectiveDocumentation {

    @Operation(summary = "Register a new retrospective")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucess! Returns the successfully created retrospective."),
                    @ApiResponse(responseCode = "403", description = "Invalid Permission! You do not have permission to acesses."),
                    @ApiResponse(responseCode = "400", description = "Bad Request! Invalid parameters"),
                    @ApiResponse(responseCode = "500", description = "Error! Could not connect to the server.")
            }
    )
    @PostMapping("/create")
    ResponseEntity<RetrospectiveDTO> create(@RequestBody @Valid RetrospectiveCreateDTO retrospectiveCreateDTO) throws NegociationRulesException;

    @Operation(summary = "Update retrospective status")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucess! Returns the successfully updated retrospective status."),
                    @ApiResponse(responseCode = "403", description = "Invalid Permission! You do not have permission to acesses."),
                    @ApiResponse(responseCode = "400", description = "Bad Request! Invalid parameters"),
                    @ApiResponse(responseCode = "500", description = "Error! Could not connect to the server.")
            }
    )
    @PutMapping("/update/{idRetrospective}/status")
    ResponseEntity<RetrospectiveDTO> updateStatus(@PathVariable("idRetrospective") Integer idRetrospective,
                                                  RetrospectiveStatus status) throws NegociationRulesException;

    @Operation(summary = "List retrospective by id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucess! Returns a retrospective by its ID."),
                    @ApiResponse(responseCode = "403", description = "Invalid Permission! You do not have permission to acesses."),
                    @ApiResponse(responseCode = "400", description = "Bad Request! Invalid parameters"),
                    @ApiResponse(responseCode = "500", description = "Error! Could not connect to the server.")
            }
    )
    @GetMapping("/list/{idRetrospective}")
    ResponseEntity<RetrospectiveDTO> listById(@PathVariable("idRetrospective") Integer idRetrospective) throws NegociationRulesException;

    @Operation(summary = "List all retrospectives associated with the sprint")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucess! Returns all retrospectives referring to ID sprint."),
                    @ApiResponse(responseCode = "403", description = "Invalid Permission! You do not have permission to acesses."),
                    @ApiResponse(responseCode = "400", description = "Bad Request! Invalid parameters"),
                    @ApiResponse(responseCode = "500", description = "Error! Could not connect to the server.")
            }
    )
    @GetMapping("/list/sprint/{idSprint}")
    ResponseEntity<PageDTO<RetrospectiveDTO>> listByIdSprint(@PathVariable("idSprint") Integer idSprint,
                                                             Integer pagina, Integer registro) throws NegociationRulesException;
}
