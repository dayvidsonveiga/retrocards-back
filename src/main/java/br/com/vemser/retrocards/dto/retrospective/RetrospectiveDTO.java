package br.com.vemser.retrocards.dto.retrospective;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RetrospectiveDTO extends RetrospectiveCreateDTO {

    @Schema(description = "Id da retrospectiva.")
    @NotNull
    private Integer idRetrospective;
}
