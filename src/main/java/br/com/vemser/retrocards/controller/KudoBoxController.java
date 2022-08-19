package br.com.vemser.retrocards.controller;

import br.com.vemser.retrocards.documentation.KudoBoxDocumentation;
import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxCreateDTO;
import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxDTO;
import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxWithCountOfItensDTO;
import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.enums.KudoStatus;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.service.KudoBoxService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/kudobox")
@Validated
@RequiredArgsConstructor
public class KudoBoxController {

    private final KudoBoxService kudoBoxService;

    @Operation(summary = "Register new kudobox")
    @PostMapping("/create")
    public ResponseEntity<KudoBoxDTO> create(@RequestBody @Valid KudoBoxCreateDTO kudoBoxCreateDTO) throws NegociationRulesException {
        return new ResponseEntity<>(kudoBoxService.create(kudoBoxCreateDTO), HttpStatus.CREATED);
    }

//    @Operation(summary = "Update status kudobox")
//    @PutMapping("/update-status/{idKudoBox}")
//    public ResponseEntity<KudoBoxDTO> updateStatus(@PathVariable("idKudoBox") Integer idKudoBox, KudoStatus kudoStatus) throws NegociationRulesException {
//        return new ResponseEntity<>(kudoBoxService.updateStatus(idKudoBox, kudoStatus), HttpStatus.OK);
//    }

    @Operation(summary = "List all the kudo boxes associated with a sprint")
    @GetMapping("/list/sprint/{idSprint}")
    public ResponseEntity<PageDTO<KudoBoxWithCountOfItensDTO>> listKudoBoxByIdSprint(@PathVariable("idSprint") Integer idSprint, Integer page, Integer quantityPerPage) throws NegociationRulesException {
        return new ResponseEntity<>(kudoBoxService.listKudoBoxByIdSprint(idSprint, page, quantityPerPage), HttpStatus.OK);
    }

    @Operation(summary = "List all the data in a kudo box")
    @GetMapping("/list/{idKudobox}")
    public ResponseEntity<KudoBoxDTO> listById(@PathVariable("idKudobox") Integer idRetrospective) throws NegociationRulesException {
        return new ResponseEntity<>(kudoBoxService.listById(idRetrospective), HttpStatus.OK);
    }
}


