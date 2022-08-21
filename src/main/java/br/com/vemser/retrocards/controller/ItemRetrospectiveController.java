package br.com.vemser.retrocards.controller;

import br.com.vemser.retrocards.dto.ItemRetrospective.ItemRetrospectiveCreateDTO;
import br.com.vemser.retrocards.dto.ItemRetrospective.ItemRetrospectiveDTO;
import br.com.vemser.retrocards.dto.ItemRetrospective.ItemRetrospectiveUpdateDTO;
import br.com.vemser.retrocards.enums.ItemType;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.service.ItemRetrospectiveService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/itemretrospective")
@Validated
@RequiredArgsConstructor
public class ItemRetrospectiveController {

    private final ItemRetrospectiveService itemRetrospectiveService;

    @Operation(summary = "Criar um novo  a new retrospective item")
    @PostMapping("/create")
    public ResponseEntity<ItemRetrospectiveDTO> create(@RequestBody @Valid ItemRetrospectiveCreateDTO itemRetrospectiveCreateDTO,
                                                       @RequestParam ItemType itemType) throws NegociationRulesException {
        return new ResponseEntity<>(itemRetrospectiveService.create(itemRetrospectiveCreateDTO, itemType), HttpStatus.CREATED);
    }

    @Operation(summary = "Update retrospective item")
    @PutMapping("/update/{idItem}")
    public ResponseEntity<ItemRetrospectiveDTO> update(@PathVariable("idItem") Integer idItem,
                                                       @RequestParam ItemType itemType,
                                                       @RequestBody ItemRetrospectiveUpdateDTO itemRetrospectiveUpdateDTO) throws NegociationRulesException {
        return new ResponseEntity<>(itemRetrospectiveService.update(idItem, itemType, itemRetrospectiveUpdateDTO), HttpStatus.OK);
    }

    @Operation(summary = "Remove retrospective item")
    @DeleteMapping("/delete/{idItem}")
    public void delete(@PathVariable("idItem") Integer idItem) throws NegociationRulesException {
        itemRetrospectiveService.delete(idItem);
    }

    @Operation(summary = "List retrospective items associated with the retrospective")
    @GetMapping("/list/retrospective/{idRetrospective}")
    public ResponseEntity<List<ItemRetrospectiveDTO>> listByIdRetrospective(@PathVariable("idRetrospective") Integer idRetrospective) throws NegociationRulesException {
        return new ResponseEntity<>(itemRetrospectiveService.listByIdRetrospective(idRetrospective), HttpStatus.OK);
    }
}
