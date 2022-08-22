package br.com.vemser.retrocards.dto.retrospective;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

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
