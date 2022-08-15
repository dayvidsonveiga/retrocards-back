package br.com.vemser.retrocards.controller;

import br.com.vemser.retrocards.dto.retrospective.Retrospective.RetrospectiveCreateDTO;
import br.com.vemser.retrocards.dto.retrospective.Retrospective.RetrospectiveDTO;
import br.com.vemser.retrocards.enums.RetrospectiveStatus;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.service.RetrospectiveService;
import lombok.RequiredArgsConstructor;
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

    @PutMapping("/{idRetrospective}")
    public ResponseEntity<RetrospectiveDTO> update(@PathVariable("idRetrospective") Integer idRetrospective,
                                         RetrospectiveCreateDTO retrospectiveCreateDTO) throws NegociationRulesException {
        return new ResponseEntity<>(retrospectiveService.update(idRetrospective, retrospectiveCreateDTO), HttpStatus.OK);
    }

    @PutMapping("/{idRetrospective}/status")
    public ResponseEntity<RetrospectiveDTO> updateStatus(@PathVariable("idRetrospective") Integer idRetrospective,
                                         RetrospectiveStatus status) throws NegociationRulesException {
        return new ResponseEntity<>(retrospectiveService.updateStatus(idRetrospective, status), HttpStatus.OK);
    }

    @DeleteMapping("/{idRetrospective}")
    public void delete(@PathVariable("idRetrospective") Integer idRetrospective) throws NegociationRulesException {
        retrospectiveService.delete(idRetrospective);
    }

    @GetMapping("/{idRetrospective}")
    public ResponseEntity<RetrospectiveDTO> listById(@PathVariable("idRetrospective") Integer idRetrospective) throws NegociationRulesException {
        return new ResponseEntity<>(retrospectiveService.listById(idRetrospective), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RetrospectiveDTO>> list() {
        return new ResponseEntity<>(retrospectiveService.list(), HttpStatus.OK);
    }
}
