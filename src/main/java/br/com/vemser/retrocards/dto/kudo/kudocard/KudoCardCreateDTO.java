package br.com.vemser.retrocards.dto.kudo.kudocard;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class KudoCardCreateDTO {

    @Schema(description = "Id do Kudo box")
    @NotNull
    private Integer idKudoBox;

    @Schema(description = "Título da Kudo card.")
    @NotBlank
    @Size(min = 3, max = 60)
    private String title;

    @Schema(description = "Data em que foi criada a Kudo card.")
    @FutureOrPresent
    private LocalDate createDate;

    @Schema(description = "Nome do usuário que submeteu a Kudo card.")
    @NotBlank
    @Email
    private String sender;

    @Schema(description = "Nome do usuário que recebeu a Kudo card.")
    @NotBlank
    @Email
    private String receiver;
}
