package br.com.vemser.retrocards.dto.kudo.kudobox;

import br.com.vemser.retrocards.enums.KudoStatus;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class KudoBoxDTO {
    @Schema(description = "Id Kudo box.")
    private Integer idKudoBox;

    @Schema(description = "Título da Kudo box.")
    @Size(min = 3, max = 60)
    private String title;

    @Schema(description = "Data de término da Kudo box.")
    private LocalDateTime endDate;

    @Schema(description = "Status da Kudo box.")
    @Hidden
    private KudoStatus status;
}
