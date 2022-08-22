package br.com.vemser.retrocards.controller;

import br.com.vemser.retrocards.documentation.EmailDocumentation;
import br.com.vemser.retrocards.dto.email.EmailCreateDTO;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController implements EmailDocumentation {

    private final EmailService emailService;

    @Operation(summary = "Enviar email relatório de retrospectiva para os usuários")
    @PostMapping("/send")
    public ResponseEntity<String> sendEmailForAllUsers(@RequestBody @Valid EmailCreateDTO emailCreateDTO,
                                                       @RequestParam Integer idRetrospective) throws NegociationRulesException {
        return new ResponseEntity<>(emailService.createEmail(emailCreateDTO, idRetrospective), HttpStatus.OK);
    }
}
