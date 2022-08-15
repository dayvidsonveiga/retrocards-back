package br.com.vemser.retrocards.controller;

import br.com.vemser.retrocards.config.Response;
import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.dto.sprint.SprintCreateDTO;
import br.com.vemser.retrocards.dto.sprint.SprintDTO;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.service.SprintService;
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


    @PostMapping("/create")
    @Response
    public ResponseEntity<SprintDTO> create(@RequestBody @Valid SprintCreateDTO sprintCreateDTO) throws NegociationRulesException {
        return new ResponseEntity<>(sprintService.create(sprintCreateDTO), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    @Response
    public ResponseEntity<PageDTO<SprintDTO>> listSprintOrderedConclusion(Integer page, Integer register) throws NegociationRulesException {
        return new ResponseEntity<>(sprintService.listSprintOrdered(page, register), HttpStatus.OK);
    }
}
