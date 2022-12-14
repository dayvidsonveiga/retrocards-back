package br.com.vemser.retrocards.controller;

import br.com.vemser.retrocards.documentation.ItemRetrospectiveDocumentation;
import br.com.vemser.retrocards.dto.itemRetrospective.ItemRetrospectiveCreateDTO;
import br.com.vemser.retrocards.dto.itemRetrospective.ItemRetrospectiveDTO;
import br.com.vemser.retrocards.dto.itemRetrospective.ItemRetrospectiveUpdateDTO;
import br.com.vemser.retrocards.enums.ItemType;
import br.com.vemser.retrocards.exceptions.NegotiationRulesException;
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
public class ItemRetrospectiveController implements ItemRetrospectiveDocumentation {

    private final ItemRetrospectiveService itemRetrospectiveService;

    @Operation(summary = "Criar um novo item de retrospectiva")
    @PostMapping("/create")
    public ResponseEntity<ItemRetrospectiveDTO> create(@RequestBody @Valid ItemRetrospectiveCreateDTO itemRetrospectiveCreateDTO,
                                                       @RequestParam ItemType itemType) throws NegotiationRulesException {
        return new ResponseEntity<>(itemRetrospectiveService.create(itemRetrospectiveCreateDTO, itemType), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar o item de retrospectiva")
    @PutMapping("/update/{idItem}")
    public ResponseEntity<ItemRetrospectiveDTO> update(@PathVariable("idItem") Integer idItem,
                                                       @RequestParam ItemType itemType,
                                                       @RequestBody ItemRetrospectiveUpdateDTO itemRetrospectiveUpdateDTO) throws NegotiationRulesException {
        return new ResponseEntity<>(itemRetrospectiveService.update(idItem, itemType, itemRetrospectiveUpdateDTO), HttpStatus.OK);
    }

    @Operation(summary = "Deletar o item de retrospectiva")
    @DeleteMapping("/delete/{idItem}")
    public void delete(@PathVariable("idItem") Integer idItem) throws NegotiationRulesException {
        itemRetrospectiveService.delete(idItem);
    }

    @Operation(summary = "Listar todos itens associados a Sprint")
    @GetMapping("/list/retrospective/{idRetrospective}")
    public ResponseEntity<List<ItemRetrospectiveDTO>> listByIdRetrospective(@PathVariable("idRetrospective") Integer idRetrospective) throws NegotiationRulesException {
        return new ResponseEntity<>(itemRetrospectiveService.listByIdRetrospective(idRetrospective), HttpStatus.OK);
    }

    @Operation(summary = "Listar item retrospectiva associado ao ID")
    @GetMapping("/list/{idItemRetrospective}")
    public ResponseEntity<ItemRetrospectiveDTO> listById(@PathVariable("idItemRetrospective") Integer idItemRetrospective) throws NegotiationRulesException {
        return new ResponseEntity<>(itemRetrospectiveService.listById(idItemRetrospective), HttpStatus.OK);
    }
}
