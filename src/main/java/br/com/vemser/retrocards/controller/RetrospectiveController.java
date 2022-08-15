package br.com.vemser.retrocards.controller;

import br.com.vemser.retrocards.dto.retrospective.Retrospective.RetrospectiveCreateDTO;
import br.com.vemser.retrocards.dto.retrospective.Retrospective.RetrospectiveDTO;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.service.RetrospectiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
}
