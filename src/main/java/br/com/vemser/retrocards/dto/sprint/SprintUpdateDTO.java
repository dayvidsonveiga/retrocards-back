package br.com.vemser.retrocards.dto.sprint;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class SprintUpdateDTO {

    @Schema(description = "Título da sprint.")
    @Size(message = "O título da sprint deve conter no mínimo 3 e no máximo 60 caracteres.", min = 3, max = 60)
    private String title;

    @Schema(description = "Data de início da sprint.")
    @FutureOrPresent
    private LocalDate startDate;

    @Schema(description = "Data de término da sprint.")
    @FutureOrPresent
    private LocalDate endDate;
}
