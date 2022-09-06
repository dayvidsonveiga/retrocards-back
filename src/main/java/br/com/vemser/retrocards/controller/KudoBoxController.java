package br.com.vemser.retrocards.controller;

import br.com.vemser.retrocards.documentation.KudoBoxDocumentation;
import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxCreateDTO;
import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxDTO;
import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxUpdateDTO;
import br.com.vemser.retrocards.dto.kudo.kudobox.KudoBoxWithCountOfItensDTO;
import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.exceptions.NegotiationRulesException;
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

    @Operation(summary = "Criar novo kudo box")
    @PostMapping("/create")
    public ResponseEntity<KudoBoxDTO> create(@RequestBody @Valid KudoBoxCreateDTO kudoBoxCreateDTO) throws NegotiationRulesException {
        return new ResponseEntity<>(kudoBoxService.create(kudoBoxCreateDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar kudo box")
    @PutMapping("/update/{idKudobox}")
    public ResponseEntity<KudoBoxDTO> update(@PathVariable("idKudobox") Integer idKudobox,
                                             @RequestBody KudoBoxUpdateDTO kudoBoxUpdateDTO) throws NegotiationRulesException {
        return new ResponseEntity<>(kudoBoxService.update(idKudobox, kudoBoxUpdateDTO), HttpStatus.OK);
    }

    @Operation(summary = "deletar kudo box")
    @DeleteMapping("/delete/{idKudoBox}")
    public ResponseEntity<Void> delete(@PathVariable("idKudoBox") Integer idKudoBox) throws NegotiationRulesException {
        kudoBoxService.delete(idKudoBox);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Listar todos os kudo boxes associados a Sprint")
    @GetMapping("/list/sprint/{idSprint}")
    public ResponseEntity<PageDTO<KudoBoxWithCountOfItensDTO>> listKudoBoxByIdSprint(@PathVariable("idSprint") Integer idSprint,
                                                                                     @RequestParam Integer page,
                                                                                     @RequestParam Integer quantityPerPage) throws NegotiationRulesException {
        return new ResponseEntity<>(kudoBoxService.listKudoBoxByIdSprint(idSprint, page, quantityPerPage), HttpStatus.OK);
    }

    @Operation(summary = "Listar o kudo box associado ao ID")
    @GetMapping("/list/{idKudobox}")
    public ResponseEntity<KudoBoxDTO> listById(@PathVariable("idKudobox") Integer idRetrospective) throws NegotiationRulesException {
        return new ResponseEntity<>(kudoBoxService.listById(idRetrospective), HttpStatus.OK);
    }
}


