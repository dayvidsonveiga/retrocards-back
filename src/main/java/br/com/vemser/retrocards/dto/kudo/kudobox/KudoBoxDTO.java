package br.com.vemser.retrocards.dto.kudo.kudobox;

import br.com.vemser.retrocards.dto.kudo.kudocard.KudoCardDTO;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

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
    private String status;

    @Schema(description = "Nome da sprint que o Kudo box foi criado.")
    private String sprint;
}
