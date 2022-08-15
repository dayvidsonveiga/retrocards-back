package br.com.vemser.retrocards.controller;

import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.dto.retrospective.Retrospective.RetrospectiveCreateDTO;
import br.com.vemser.retrocards.dto.retrospective.Retrospective.RetrospectiveDTO;
import br.com.vemser.retrocards.enums.RetrospectiveStatus;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.service.RetrospectiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/retrospective")
@Validated
@RequiredArgsConstructor
public class RetrospectiveController {

    private final RetrospectiveService retrospectiveService;

    @PostMapping("/create")
    public ResponseEntity<RetrospectiveDTO> create(@RequestBody @Valid RetrospectiveCreateDTO retrospectiveCreateDTO) throws NegociationRulesException {
        return new ResponseEntity<>(retrospectiveService.create(retrospectiveCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update/{idRetrospective}")
    public ResponseEntity<RetrospectiveDTO> update(@PathVariable("idRetrospective") Integer idRetrospective,
                                         RetrospectiveCreateDTO retrospectiveCreateDTO) throws NegociationRulesException {
        return new ResponseEntity<>(retrospectiveService.update(idRetrospective, retrospectiveCreateDTO), HttpStatus.OK);
    }

    @PutMapping("/update/{idRetrospective}/status")
    public ResponseEntity<RetrospectiveDTO> updateStatus(@PathVariable("idRetrospective") Integer idRetrospective,
                                         RetrospectiveStatus status) throws NegociationRulesException {
        return new ResponseEntity<>(retrospectiveService.updateStatus(idRetrospective, status), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{idRetrospective}")
    public void delete(@PathVariable("idRetrospective") Integer idRetrospective) throws NegociationRulesException {
        retrospectiveService.delete(idRetrospective);
    }

    @GetMapping("/list")
    public ResponseEntity<List<RetrospectiveDTO>> listAll() {
        return new ResponseEntity<>(retrospectiveService.listAll(), HttpStatus.OK);
    }

    @GetMapping("/list/{idRetrospective}")
    public ResponseEntity<RetrospectiveDTO> listById(@PathVariable("idRetrospective") Integer idRetrospective) throws NegociationRulesException {
        return new ResponseEntity<>(retrospectiveService.listById(idRetrospective), HttpStatus.OK);
    }

    @GetMapping("/list/sprint/{idSprint}")
    public ResponseEntity<PageDTO<RetrospectiveDTO>> listByIdSprint(@PathVariable("idSprint") Integer idSprint, Integer pagina, Integer registro) throws NegociationRulesException {
        return new ResponseEntity<>(retrospectiveService.listRetrospectiveByIdSprint(idSprint, pagina, registro), HttpStatus.OK);
    }
}
