package br.com.vemser.retrocards.dto.sprint;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class SprintCreateDTO {

    @Schema(description = "Título da sprint.")
    @NotBlank
    @Size(min = 3, max = 60)
    private String title;

    @Schema(description = "Data de início da sprint.")
    @NotNull
    private LocalDate startDate;

    @Schema(description = "Data de término da sprint.")
    @NotNull
    private LocalDate endDate;

}