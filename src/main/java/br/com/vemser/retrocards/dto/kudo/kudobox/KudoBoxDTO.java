package br.com.vemser.retrocards.dto.kudo.kudobox;

import br.com.vemser.retrocards.enums.KudoStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class KudoBoxDTO {

    @Schema(description = "Título da Kudo box.")
    @NotBlank
    private String title;

    @Schema(description = "Data de término da Kudo box.")
    @FutureOrPresent
    @NotNull
    private LocalDate endDate;

    @Schema(description = "Status da Kudo box.")
    @NotNull
    private KudoStatus status;
}
