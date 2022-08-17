package br.com.vemser.retrocards.controller;

import br.com.vemser.retrocards.dto.email.EmailCreateDTO;
import br.com.vemser.retrocards.dto.email.EmailDTO;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.service.EmailService;
import freemarker.template.TemplateException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @Operation(summary = "Send email for users")
    @PostMapping
    public ResponseEntity<EmailDTO> sendEmailForAllUsers(@RequestBody @Valid EmailCreateDTO emailCreateDTO, Integer idRetrospective) throws MessagingException, TemplateException, IOException {
        return new ResponseEntity<>(emailService.createEmail(emailCreateDTO, idRetrospective), HttpStatus.OK);
    }
}
