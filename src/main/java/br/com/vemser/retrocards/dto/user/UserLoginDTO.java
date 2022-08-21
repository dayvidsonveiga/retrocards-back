package br.com.vemser.retrocards.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserLoginDTO {

    @Schema(example = "danyllo@gmail.com")
    @NotBlank(message = "O campo de email não pode ser nulo/vazio.")
    @Email(message = "Deve ser informado um e-mail válido!")
    private String email;

    @Schema(example = "123")
    @NotBlank(message = "O campo de password não pode ser vazio/nulo.")
    private String password;
}
