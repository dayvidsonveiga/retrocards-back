package br.com.vemser.retrocards.dto.kudo.kudocard;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class KudoCardCreateDTO {

    @Schema(description = "Título da Kudo card.")
    @NotBlank
    private String title;

    @Schema(description = "Data em que foi criada a Kudo card.")
    @FutureOrPresent
    private LocalDate createDate;

    @Schema(description = "Nome do usuário que submeteu a Kudo card.")
    @NotBlank
    private String sender;

    @Schema(description = "Nome do usuário que recebeu a Kudo card.")
    @NotBlank
    private String receiver;
}
