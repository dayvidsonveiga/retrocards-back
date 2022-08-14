package br.com.vemser.retrocards.dto.kudo.kudobox;

import br.com.vemser.retrocards.enums.KudoStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class KudoBoxDTO extends KudoBoxCreateDTO {

    @Schema(description = "Id Kudo box.")
    @NotNull
    private Integer idKudoBox;
}
