package br.com.vemser.retrocards.dto.retrospective;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class RetrospectiveUpdateDTO {

    @Schema(description = "Título da retrospectiva.")
    @Size(min = 3, max = 60)
    private String title;

    @Schema(description = "Data que ocorreu a retrospectiva.")
    @FutureOrPresent
    private LocalDate occurredDate;

}
