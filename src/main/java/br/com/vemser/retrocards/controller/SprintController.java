package br.com.vemser.retrocards.controller;

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
import java.util.List;

@RestController
@RequestMapping("/sprint")
@Validated
@RequiredArgsConstructor
public class SprintController {

    private final SprintService sprintService;

    @GetMapping("/list-sprint-conclusion")
    public ResponseEntity<List<SprintDTO>> listSprintOrderedConclusion() throws NegociationRulesException {
        return new ResponseEntity<>(sprintService.listSprintOrdered(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<SprintDTO> createSprint(@RequestBody @Valid SprintCreateDTO sprintCreateDTO) throws NegociationRulesException {
        return new ResponseEntity<>(sprintService.create(sprintCreateDTO), HttpStatus.CREATED);
    }
}
