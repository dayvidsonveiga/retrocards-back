package br.com.vemser.retrocards.dto.sprint;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class SprintCreateDTO {

    @Schema(description = "Título da sprint.")
    @NotBlank(message = "O título da sprint não pode ser vazio/nulo.")
    @Size(message = "O título da sprint deve conter no mínimo 3 e no máximo 60 caracteres.", min = 3, max = 60)
    private String title;

    @Schema(description = "Data de início da sprint.")
    @NotNull(message = "A data de início da sprint não pode ser vazia/nula.")
    @FutureOrPresent
    private LocalDate startDate;

    @Schema(description = "Data de término da sprint.")
    @NotNull(message = "A data de término da sprint não pode ser vazia/nula.")
    @FutureOrPresent
    private LocalDate endDate;
}