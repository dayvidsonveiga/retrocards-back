package br.com.vemser.retrocards.dto.user;

import br.com.vemser.retrocards.entity.RolesEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserDTO {

    @Schema(description = "Id do Usuario")
    private Integer idUser;

    @Schema(description = "Nome do Usuario")
    private String name;

    @Schema(description = "Email do Usuario")
    private String email;

    @Schema(description = "Cargo do Usuario")
    private RolesEntity role;
}
