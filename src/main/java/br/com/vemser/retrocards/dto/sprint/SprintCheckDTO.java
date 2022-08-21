package br.com.vemser.retrocards.dto.sprint;

import br.com.vemser.retrocards.enums.SprintStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SprintCheckDTO {

    @Schema(description = "Id da sprint")
    private Integer idSprint;

    @Schema(description = "Título da sprint.")
    private String title;

    @Schema(description = "Data de início da sprint.")
    private LocalDateTime startDate;

    @Schema(description = "Data de término da sprint.")
    private LocalDateTime endDate;

    @Schema(description = "Status da Sprint")
    private SprintStatus status;

    @Schema(description = "Verifica se há alguma retrospectiva em andamento.")
    private Boolean verifyRetrospective;

    @Schema(description = "Verifica se há alguma kudo box em andamento.")
    private Boolean verifyKudoBox;
}
