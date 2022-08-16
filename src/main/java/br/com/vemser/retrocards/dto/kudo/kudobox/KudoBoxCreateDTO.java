package br.com.vemser.retrocards.dto.kudo.kudobox;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class KudoBoxCreateDTO {

    @Schema(description = "Identificador único da sprint")
    private Integer idSprint;

    @Schema(description = "Título da Kudo box.")
    @NotBlank
    @Size(min = 3, max = 60)
    private String title;

    @Schema(description = "Data de término da Kudo box.")
    @FutureOrPresent
    @NotNull
    private LocalDate endDate;

    @Schema(description = "Status da Kudo box.")
    @Hidden
    private String status;
}
