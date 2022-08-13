package br.com.vemser.retrocards.dto.retrospective;

import br.com.vemser.retrocards.enums.RetrospectiveStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
public class RetrospectiveDTO {

    @Schema(description = "Título da retrospectiva.")
    @NotBlank
    private String title;

    @Schema(description = "Data que ocorreu a retrospectiva.")
    @PastOrPresent
    private LocalDate occurredDate;

    @Schema(description = "Status da retrospectiva.")
    @NotNull
    private RetrospectiveStatus status;
}