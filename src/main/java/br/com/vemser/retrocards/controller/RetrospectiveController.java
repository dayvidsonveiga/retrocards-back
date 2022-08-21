package br.com.vemser.retrocards.controller;

import br.com.vemser.retrocards.documentation.RetrospectiveDocumentation;
import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.dto.retrospective.RetrospectiveCreateDTO;
import br.com.vemser.retrocards.dto.retrospective.RetrospectiveDTO;
import br.com.vemser.retrocards.dto.retrospective.RetrospectiveUpdateDTO;
import br.com.vemser.retrocards.dto.retrospective.RetrospectiveWithCountOfItensDTO;
import br.com.vemser.retrocards.enums.RetrospectiveStatus;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.service.RetrospectiveService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/retrospective")
@Validated
@RequiredArgsConstructor
public class RetrospectiveController implements RetrospectiveDocumentation {

    private final RetrospectiveService retrospectiveService;

    @Operation(summary = "Criar nova retrospectiva")
    @PostMapping("/create")
    public ResponseEntity<RetrospectiveDTO> create(@RequestBody @Valid RetrospectiveCreateDTO retrospectiveCreateDTO) throws NegociationRulesException {
        return new ResponseEntity<>(retrospectiveService.create(retrospectiveCreateDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar retrospectiva")
    @PutMapping("/update/{idRetrospective}")
    public ResponseEntity<RetrospectiveDTO> update(@PathVariable("idRetrospective") Integer idRetrospective,
                                                   @RequestBody RetrospectiveUpdateDTO retrospectiveUpdateDTO) throws NegociationRulesException {
        return new ResponseEntity<>(retrospectiveService.update(idRetrospective, retrospectiveUpdateDTO), HttpStatus.OK);
    }

    @Operation(summary = "Atualizar status da retrospectiva")
    @PutMapping("/update-status/{idRetrospective}")
    public ResponseEntity<RetrospectiveDTO> updateStatus(@PathVariable("idRetrospective") Integer idRetrospective,
                                                         @RequestParam RetrospectiveStatus status) throws NegociationRulesException {
        return new ResponseEntity<>(retrospectiveService.updateStatus(idRetrospective, status), HttpStatus.OK);
    }

    @Operation(summary = "Deletar retrospectiva")
    @DeleteMapping("/delete/{idRetrospective}")
    public ResponseEntity<Void> delete(@PathVariable("idRetrospective") Integer idRetrospective) throws NegociationRulesException {
        retrospectiveService.delete(idRetrospective);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Listar retrospectiva associada ao ID")
    @GetMapping("/list/{idRetrospective}")
    public ResponseEntity<RetrospectiveDTO> listById(@PathVariable("idRetrospective") Integer idRetrospective) throws NegociationRulesException {
        return new ResponseEntity<>(retrospectiveService.listById(idRetrospective), HttpStatus.OK);
    }

    @Operation(summary = "Lista todas as retrospectivas associadas ao ID da sprint")
    @GetMapping("/list/sprint/{idSprint}")
    public ResponseEntity<PageDTO<RetrospectiveWithCountOfItensDTO>> listByIdSprint(@PathVariable("idSprint") Integer idSprint,
                                                                                    @RequestParam Integer page,
                                                                                    @RequestParam Integer quantityPerPage) throws NegociationRulesException {
        return new ResponseEntity<>(retrospectiveService.listRetrospectiveByIdSprint(idSprint, page, quantityPerPage), HttpStatus.OK);
    }
}
