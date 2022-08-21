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
                    @ApiResponse(responseCode = "200", description = "Sucess! Returns all kudoboxes referring to ID sprint."),
                    @ApiResponse(responseCode = "403", description = "Invalid Permission! You do not have permission to acesses."),
                    @ApiResponse(responseCode = "400", description = "Bad Request! Invalid parameters"),
                    @ApiResponse(responseCode = "500", description = "Error! Could not connect to the server.")
            }
    )
    @PostMapping("/create")
    public ResponseEntity<ItemRetrospectiveDTO> create(@RequestBody @Valid ItemRetrospectiveCreateDTO itemRetrospectiveCreateDTO, ItemType itemType) throws NegociationRulesException;

    @Operation(summary = "Update retrospective item")
    @PutMapping("/update/{idItem}")
    public ResponseEntity<ItemRetrospectiveDTO> update(@PathVariable("idItem") Integer idItem, ItemType itemType,
                                                       @RequestBody ItemRetrospectiveUpdateDTO itemRetrospectiveUpdateDTO) throws NegociationRulesException;

    @Operation(summary = "Remove retrospective item")
    @DeleteMapping("/delete/{idItem}")
    public void delete(@PathVariable("idItem") Integer idItem) throws NegociationRulesException;

    @Operation(summary = "List retrospective items associated with the retrospective")
    @GetMapping("/list/retrospective/{idRetrospective}")
    public ResponseEntity<List<ItemRetrospectiveDTO>> listByIdRetrospective(@PathVariable("idRetrospective") Integer idRetrospective) throws NegociationRulesException;
}
