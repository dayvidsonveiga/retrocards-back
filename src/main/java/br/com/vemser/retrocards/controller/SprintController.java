package br.com.vemser.retrocards.controller;

import br.com.vemser.retrocards.documentation.SprintDocumentation;
import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.dto.sprint.SprintCreateDTO;
import br.com.vemser.retrocards.dto.sprint.SprintDTO;
import br.com.vemser.retrocards.dto.sprint.SprintUpdateDTO;
import br.com.vemser.retrocards.dto.sprint.SprintWithEndDateDTO;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.service.SprintService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/sprint")
@Validated
@RequiredArgsConstructor
public class SprintController {

    private final SprintService sprintService;

    @Operation(summary = "Register new sprint")
    @PostMapping("/create")
    public ResponseEntity<SprintDTO> create(@RequestBody @Valid SprintCreateDTO sprintCreateDTO) throws NegociationRulesException {
        return new ResponseEntity<>(sprintService.create(sprintCreateDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Update sprint")
    @PutMapping("/update/{idSprint}")
    public ResponseEntity<SprintDTO> update(@PathVariable("idSprint") Integer idSprint, @RequestBody @Valid SprintUpdateDTO sprintUpdateDTO) throws NegociationRulesException {
        return new ResponseEntity<>(sprintService.update(idSprint, sprintUpdateDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete sprint")
    @DeleteMapping("/delete/{idSprint}")
    public ResponseEntity<Void> delete(@PathVariable("idSprint") Integer idSprint) throws NegociationRulesException {
        sprintService.delete(idSprint);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "List sprint in order of completion")
    @GetMapping("/list")
    public ResponseEntity<PageDTO<SprintWithEndDateDTO>> listByDateDesc(Integer page, Integer quantityPerPage) throws NegociationRulesException {
        return new ResponseEntity<>(sprintService.listByDateDesc(page, quantityPerPage), HttpStatus.OK);
    }
}
