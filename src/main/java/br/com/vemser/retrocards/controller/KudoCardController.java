package br.com.vemser.retrocards.controller;

import br.com.vemser.retrocards.documentation.KudoCardDocumentation;
import br.com.vemser.retrocards.dto.kudo.kudocard.KudoCardCreateDTO;
import br.com.vemser.retrocards.dto.kudo.kudocard.KudoCardDTO;
import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.service.KudoCardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/kudocard")
@Validated
@RequiredArgsConstructor
public class KudoCardController implements KudoCardDocumentation {

    private final KudoCardService kudoCardService;

    @Operation(summary = "Register new kudocard")
    @PostMapping("/create")
    public ResponseEntity<KudoCardDTO> create(@RequestBody @Valid KudoCardCreateDTO kudoCardCreateDTO) throws NegociationRulesException {
        return new ResponseEntity<>(kudoCardService.create(kudoCardCreateDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "List all the kudo cards")
    @GetMapping("/list")
    public ResponseEntity<PageDTO<KudoCardDTO>> listAll(Integer pagina, Integer registros) throws NegociationRulesException {
        return new ResponseEntity<>(kudoCardService.listAll(pagina, registros), HttpStatus.OK);
    }

    @Operation(summary = "List all the kudo cards associated with the kudo box")
    @GetMapping("/list/kudobox/{idKudoBox}")
    public ResponseEntity<PageDTO<KudoCardDTO>> listKudoCardByIdKudoBox(@PathVariable("idKudoBox") Integer idKudoBox, Integer pagina, Integer registros) throws NegociationRulesException {
        return new ResponseEntity<>(kudoCardService.listKudoCardByIdKudoBox(idKudoBox, pagina, registros), HttpStatus.OK);
    }
}
