package br.com.vemser.retrocards.dto.sprint;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SprintDTO extends SprintCreateDTO {

    @Schema(description = "Id da sprint")
    @NotNull
    private Integer idSprint;
}
