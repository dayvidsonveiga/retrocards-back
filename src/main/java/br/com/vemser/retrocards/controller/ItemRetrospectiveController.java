package br.com.vemser.retrocards.controller;

import br.com.vemser.retrocards.dto.retrospective.ItemRetrospective.ItemRetrospectiveCreateDTO;
import br.com.vemser.retrocards.dto.retrospective.ItemRetrospective.ItemRetrospectiveDTO;
import br.com.vemser.retrocards.dto.retrospective.ItemRetrospective.ItemRetrospectiveUpdateDTO;
import br.com.vemser.retrocards.enums.ItemType;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.service.ItemRetrospectiveService;
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

    @PostMapping("/create")
    public ResponseEntity<ItemRetrospectiveDTO> create(@RequestBody @Valid ItemRetrospectiveCreateDTO itemRetrospectiveCreateDTO, ItemType itemType) throws NegociationRulesException {
        return new ResponseEntity<>(itemRetrospectiveService.create(itemRetrospectiveCreateDTO, itemType), HttpStatus.CREATED);
    }

    @PutMapping("/update/{idItem}")
    public ResponseEntity<ItemRetrospectiveDTO> update(@PathVariable("idItem") Integer idItem, ItemType itemType,
                                                       @RequestBody @Valid ItemRetrospectiveUpdateDTO itemRetrospectiveUpdateDTO) throws Exception {
        return new ResponseEntity<>(itemRetrospectiveService.update(idItem, itemType, itemRetrospectiveUpdateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{idItem}")
    public void delete(@PathVariable("idItem") Integer idItem) throws NegociationRulesException {
        itemRetrospectiveService.delete(idItem);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ItemRetrospectiveDTO>> list() {
        return new ResponseEntity<>(itemRetrospectiveService.list(), HttpStatus.OK);
    }

    @GetMapping("/list/{idItem}")
    public ResponseEntity<ItemRetrospectiveDTO> listByIdItem(@PathVariable("idItem") Integer idItem) throws NegociationRulesException {
        return new ResponseEntity<>(itemRetrospectiveService.listByIdItem(idItem), HttpStatus.OK);
    }

    @GetMapping("/list/retrospective/{idRetrospective}")
    public ResponseEntity<List<ItemRetrospectiveDTO>> listByIdRetrospective(@PathVariable("idRetrospective") Integer idRetrospective) {
        return new ResponseEntity<>(itemRetrospectiveService.listByIdRetrospective(idRetrospective), HttpStatus.OK);
    }

}
