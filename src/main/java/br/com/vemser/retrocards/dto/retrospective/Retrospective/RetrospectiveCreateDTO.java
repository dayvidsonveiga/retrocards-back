package br.com.vemser.retrocards.dto.retrospective.Retrospective;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class RetrospectiveCreateDTO {

    @Schema(description = "Id da sprint")
    @NotNull
    private Integer idSprint;

    @Schema(description = "Título da retrospectiva.")
    @NotBlank
    @Size(min = 3, max = 60)
    private String title;

    @Schema(description = "Data que ocorreu a retrospectiva.")
    @FutureOrPresent
    @NotNull
    private LocalDate occurredDate;

    @Schema(description = "Status da retrospectiva.")
    @Hidden
    private String status;
}
