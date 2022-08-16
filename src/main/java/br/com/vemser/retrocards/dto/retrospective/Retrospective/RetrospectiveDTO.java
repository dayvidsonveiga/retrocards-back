package br.com.vemser.retrocards.dto.retrospective.Retrospective;

import br.com.vemser.retrocards.enums.RetrospectiveStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class RetrospectiveDTO {

    @Schema(description = "Id da retrospectiva.")
    private Integer idRetrospective;

    @Schema(description = "Título da retrospectiva.")
    private String title;

    @Schema(description = "Data que ocorreu a retrospectiva.")
    private LocalDateTime occurredDate;

    @Schema(description = "Status da retrospectiva.")
    private RetrospectiveStatus status;

}
