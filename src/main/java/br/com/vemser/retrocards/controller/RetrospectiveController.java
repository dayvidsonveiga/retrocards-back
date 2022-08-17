package br.com.vemser.retrocards.controller;

import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.dto.retrospective.RetrospectiveCreateDTO;
import br.com.vemser.retrocards.dto.retrospective.RetrospectiveDTO;
import br.com.vemser.retrocards.dto.retrospective.RetrospectiveWithCountOfItensDTO;
import br.com.vemser.retrocards.enums.RetrospectiveStatus;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.service.RetrospectiveService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/retrospective")
@Validated
@RequiredArgsConstructor
public class RetrospectiveController {

    private final RetrospectiveService retrospectiveService;

    @Operation(summary = "Register a new retrospective")
    @PostMapping("/create")
    public ResponseEntity<RetrospectiveDTO> create(@RequestBody @Valid RetrospectiveCreateDTO retrospectiveCreateDTO) throws NegociationRulesException {
        return new ResponseEntity<>(retrospectiveService.create(retrospectiveCreateDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Update retrospective status")
    @PutMapping("/update/{idRetrospective}/status")
    public ResponseEntity<RetrospectiveDTO> updateStatus(@PathVariable("idRetrospective") Integer idRetrospective,
                                         RetrospectiveStatus status) throws NegociationRulesException {
        return new ResponseEntity<>(retrospectiveService.updateStatus(idRetrospective, status), HttpStatus.OK);
    }

//    @Operation(summary = "Remove retrospective")
//    @DeleteMapping("/delete/{idRetrospective}")
//    public ResponseEntity<Void> delete(@PathVariable("idRetrospective") Integer idRetrospective) throws NegociationRulesException {
//        retrospectiveService.delete(idRetrospective);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @Operation(summary = "List all retrospectives associated with the sprint")
    @GetMapping("/list/sprint/{idSprint}")
    public ResponseEntity<PageDTO<RetrospectiveWithCountOfItensDTO>> listByIdSprint(@PathVariable("idSprint") Integer idSprint, Integer pagina, Integer registro) throws NegociationRulesException {
        return new ResponseEntity<>(retrospectiveService.listRetrospectiveByIdSprint(idSprint, pagina, registro), HttpStatus.OK);
    }
}
