package br.com.vemser.retrocards.controller;

import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxCreateDTO;
import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxDTO;
import br.com.vemser.retrocards.dto.kudo.kudocard.KudoCardCreateDTO;
import br.com.vemser.retrocards.dto.kudo.kudocard.KudoCardDTO;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.service.KudoCardService;
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
@RequestMapping("/kudocard")
@Validated
@RequiredArgsConstructor
public class KudoCardController {

    private final KudoCardService kudoCardService;

    @PostMapping("/create")
    public ResponseEntity<KudoCardDTO> createKudoCard(@RequestBody @Valid KudoCardCreateDTO kudoCardCreateDTO) throws NegociationRulesException {
        return new ResponseEntity<>(kudoCardService.create(kudoCardCreateDTO), HttpStatus.CREATED);
    }
}
