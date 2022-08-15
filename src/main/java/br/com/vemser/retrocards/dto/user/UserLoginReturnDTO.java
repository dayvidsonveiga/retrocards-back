package br.com.vemser.retrocards.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginReturnDTO {

    @Schema(description = "Nome do Usuario")
    @NotBlank
    private String name;

    @Schema(description = "Cargo do Usuario")
    @NotBlank
    private String role;

    @Schema(description = "Token do Usuario")
    @NotBlank
    private String token;
}
