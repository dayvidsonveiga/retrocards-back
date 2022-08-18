package br.com.vemser.retrocards.dto.email;

import br.com.vemser.retrocards.dto.retrospective.RetrospectiveDTO;
import br.com.vemser.retrocards.dto.retrospective.RetrospectiveEmailDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
public class EmailDTO {

    @Schema(description = "Id email")
    @NotNull
    private Integer idEmail;

    @Schema(description = "Receiver")
    @NotBlank
    private String receiver;

    @Schema(description = "Subject")
    @NotBlank
    private String subject;

    @Schema(description = "Send date")
    @NotNull
    private LocalDate sendDate;

    @Schema(description = "Retrospective")
    @NotNull
    private RetrospectiveEmailDTO retrospectiveEmailDTO;
}
