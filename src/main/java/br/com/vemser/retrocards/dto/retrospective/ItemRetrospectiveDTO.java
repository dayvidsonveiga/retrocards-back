package br.com.vemser.retrocards.dto.retrospective;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ItemRetrospectiveDTO extends ItemRetrospectiveCreateDTO {

    @Schema(description = "Id item retrospectiva.")
    @NotNull
    private Integer idItemRetrospective;
}
