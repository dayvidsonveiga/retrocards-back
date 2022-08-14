package br.com.vemser.retrocards.controller;

import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxCreateDTO;
import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxDTO;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.service.KudoBoxService;
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
@RequestMapping("/kudobox")
@Validated
@RequiredArgsConstructor
public class KudoBoxController {

    private final KudoBoxService kudoBoxService;


    @PostMapping("/create")
    public ResponseEntity<KudoBoxDTO> createKudoBox(@RequestBody @Valid KudoBoxCreateDTO kudoBoxCreateDTO) throws NegociationRulesException {
        return new ResponseEntity<>(kudoBoxService.create(kudoBoxCreateDTO), HttpStatus.CREATED);
    }
}


