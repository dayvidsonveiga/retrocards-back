package br.com.vemser.retrocards.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserCreateDTO {

    @Schema(description = "Nome do Usuario")
    @NotBlank
    @Size(min = 3, max = 60)
    private String name;

    @Schema(example = "nome@gmail.com")
    @NotBlank
    @Email(message = "Deve ser informado um e-mail válido.")
    private String email;

    @Schema(example = "123")
    @NotBlank
    private String password;
}
