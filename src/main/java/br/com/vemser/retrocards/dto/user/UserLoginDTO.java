package br.com.vemser.retrocards.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginDTO {

    @Schema(example = "danyllo@gmail.com")
    @NotBlank
    private String email;

    @Schema(example = "123")
    @NotBlank
    private String password;
}
