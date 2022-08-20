package br.com.vemser.retrocards.dto.kudo.kudobox;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;

@Data
public class KudoBoxUpdateDTO {


    @Schema(description = "Título da Kudo box.")
    private String title;

    @Schema(description = "Data de término da Kudo box.")
    @FutureOrPresent
    private LocalDate endDate;
}
