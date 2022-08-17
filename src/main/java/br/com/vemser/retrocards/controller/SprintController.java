package br.com.vemser.retrocards.controller;

import br.com.vemser.retrocards.documentation.SprintDocumentation;
import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.dto.sprint.SprintCreateDTO;
import br.com.vemser.retrocards.dto.sprint.SprintDTO;
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

    @Operation(summary = "List sprint in order of completion")
    @GetMapping("/list")
    public ResponseEntity<PageDTO<SprintWithEndDateDTO>> listByDateDesc(Integer page, Integer register) throws NegociationRulesException {
        return new ResponseEntity<>(sprintService.listByDateDesc(page, register), HttpStatus.OK);
    }
}
