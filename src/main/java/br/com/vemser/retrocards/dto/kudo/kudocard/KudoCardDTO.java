package br.com.vemser.retrocards.dto.kudo.kudocard;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class KudoCardDTO extends KudoCardCreateDTO {

    @Schema(description = "Id Kudo card.")
    @NotNull
    private Integer idKudoCard;
}
