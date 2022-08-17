package br.com.vemser.retrocards.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserNomeEmailDTO {

    @Schema(example = "danyllo@gmail.com")
    private String email;

    @Schema(description = "Nome do Usuario")
    private String name;
}
