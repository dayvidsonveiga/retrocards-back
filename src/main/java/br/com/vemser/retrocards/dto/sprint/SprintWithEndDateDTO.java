package br.com.vemser.retrocards.dto.sprint;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SprintWithEndDateDTO {

    @Schema(description = "Id da sprint")
    private Integer idSprint;

    @Schema(description = "Título da sprint.")
    private String title;

    @Schema(description = "Data de término da sprint.")
    private LocalDateTime endDate;
}
