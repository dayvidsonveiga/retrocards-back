package br.com.vemser.retrocards.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserDTO {

    @Schema(description = "Id do Usuario")
    @NotNull
    private Integer idUser;

    @Schema(description = "Nome do Usuario")
    @NotBlank
    private String name;

    @Schema(description = "Email do Usuario")
    @NotBlank
    private String email;

    @Schema(description = "Cargo do Usuario")
    @NotBlank
    private String role;
}
