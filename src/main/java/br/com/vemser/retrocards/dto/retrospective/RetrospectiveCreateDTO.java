package br.com.vemser.retrocards.dto.retrospective;

import br.com.vemser.retrocards.enums.RetrospectiveStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class RetrospectiveCreateDTO {

    @Schema(description = "Título da retrospectiva.")
    @NotBlank
    @Size(min = 3, max = 60)
    private String title;

    @Schema(description = "Data que ocorreu a retrospectiva.")
    @PastOrPresent
    private LocalDate occurredDate;

    @Schema(description = "Status da retrospectiva.")
    @NotNull
    private RetrospectiveStatus status;
}
