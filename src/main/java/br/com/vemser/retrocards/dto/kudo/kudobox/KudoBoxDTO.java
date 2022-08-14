package br.com.vemser.retrocards.dto.kudo.kudobox;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class KudoBoxDTO extends KudoBoxCreateDTO {

    @Schema(description = "Id Kudo box.")
    @NotNull
    private Integer idKudoBox;
}
