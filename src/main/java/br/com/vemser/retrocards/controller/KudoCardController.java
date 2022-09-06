package br.com.vemser.retrocards.controller;

import br.com.vemser.retrocards.documentation.KudoCardDocumentation;
import br.com.vemser.retrocards.dto.kudo.kudocard.KudoCardCreateDTO;
import br.com.vemser.retrocards.dto.kudo.kudocard.KudoCardDTO;
import br.com.vemser.retrocards.dto.kudo.kudocard.KudoCardUpdateDTO;
import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.exceptions.NegotiationRulesException;
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

    @Operation(summary = "Criar novo kudo card")
    @PostMapping("/create")
    public ResponseEntity<KudoCardDTO> create(@RequestBody @Valid KudoCardCreateDTO kudoCardCreateDTO) throws NegotiationRulesException {
        return new ResponseEntity<>(kudoCardService.create(kudoCardCreateDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar kudo card")
    @PutMapping("/update/{idKudoCard}")
    public ResponseEntity<KudoCardDTO> update(@PathVariable("idKudoCard") Integer idKudoCard,
                                              @RequestBody KudoCardUpdateDTO kudoCardUpdateDTO) throws NegotiationRulesException {
        return new ResponseEntity<>(kudoCardService.update(idKudoCard, kudoCardUpdateDTO), HttpStatus.OK);
    }

    @Operation(summary = "Deletar kudo card")
    @DeleteMapping("/delete/{idKudoCard}")
    public ResponseEntity<Void> delete(@PathVariable("idKudoCard") Integer idKudoCard) throws NegotiationRulesException {
        kudoCardService.delete(idKudoCard);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Listar todos os kudo cards associados ao kudo box")
    @GetMapping("/list/kudocards/{idKudoBox}")
    public ResponseEntity<PageDTO<KudoCardDTO>> listKudoCardByIdKudoBox(@PathVariable("idKudoBox") Integer idKudoBox,
                                                                        @RequestParam Integer page,
                                                                        @RequestParam Integer quantityPerPage) throws NegotiationRulesException {
        return new ResponseEntity<>(kudoCardService.listKudoCardByIdKudoBox(idKudoBox, page, quantityPerPage), HttpStatus.OK);
    }

    @Operation(summary = "Lista kudo card associado ao ID")
    @GetMapping("/list/{idKudoCard}")
    public ResponseEntity<KudoCardDTO> listById(@PathVariable("idKudoCard") Integer idKudoCard) throws NegotiationRulesException {
        return new ResponseEntity<>(kudoCardService.listById(idKudoCard), HttpStatus.OK);
    }
}
