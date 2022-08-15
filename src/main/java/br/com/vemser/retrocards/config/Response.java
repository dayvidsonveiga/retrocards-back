package br.com.vemser.retrocards.config;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(
        value = {
                @ApiResponse(responseCode = "200", description = "Sucesso! Operação concluída com sucesso."),
                @ApiResponse(responseCode = "401", description = "Permissão inválida! Ação não autorizada."),
                @ApiResponse(responseCode = "400", description = "Bad Request! Parâmetros inválidos"),
                @ApiResponse(responseCode = "404", description = "Página não encontrada."),
                @ApiResponse(responseCode = "500", description = "Erro! Não foi possível conectar com o servidor.")
        }
)
public @interface Response {

}
