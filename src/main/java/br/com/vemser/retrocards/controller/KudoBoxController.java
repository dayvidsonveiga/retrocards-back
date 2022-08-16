package br.com.vemser.retrocards.controller;

import br.com.vemser.retrocards.documentation.KudoBoxDocumentation;
import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxCreateDTO;
import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxDTO;
import br.com.vemser.retrocards.dto.page.PageDTO;
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
public class KudoBoxController implements KudoBoxDocumentation {

    private final KudoBoxService kudoBoxService;

    @Operation(summary = "Register new kudobox")
    @PostMapping("/create")
    public ResponseEntity<KudoBoxDTO> create(@RequestBody @Valid KudoBoxCreateDTO kudoBoxCreateDTO) throws NegociationRulesException {
        return new ResponseEntity<>(kudoBoxService.create(kudoBoxCreateDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete kudobox")
    @DeleteMapping("/delete/{idKudoBox}")
    public ResponseEntity<Void> delete(@PathVariable("idKudoBox") Integer idKudoBox) throws NegociationRulesException {
        kudoBoxService.delete(idKudoBox);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "List all the kudo boxes associated with a sprint")
    @GetMapping("/list/sprint/{idSprint}")
    public ResponseEntity<PageDTO<KudoBoxDTO>> listKudoBoxByIdSprint(@PathVariable("idSprint") Integer idSprint, Integer pagina, Integer registros) throws NegociationRulesException {
        return new ResponseEntity<>(kudoBoxService.listKudoBoxByIdSprint(idSprint, pagina, registros), HttpStatus.OK);
    }
}


