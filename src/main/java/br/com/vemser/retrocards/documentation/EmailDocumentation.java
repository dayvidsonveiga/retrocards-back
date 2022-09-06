package br.com.vemser.retrocards.documentation;

import br.com.vemser.retrocards.dto.email.EmailCreateDTO;
import br.com.vemser.retrocards.exceptions.NegotiationRulesException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface EmailDocumentation {

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Email foi enviado."),
                    @ApiResponse(responseCode = "403", description = "Permissão inválida! Você não possui permissão para acessar esse serviço."),
                    @ApiResponse(responseCode = "500", description = "Erro! Falha ao conectar com os servidores.")
            }
    )
    @PostMapping("/send")
    ResponseEntity<String> sendEmailForAllUsers(@RequestBody @Valid EmailCreateDTO emailCreateDTO, Integer idRetrospective) throws NegotiationRulesException;
}
