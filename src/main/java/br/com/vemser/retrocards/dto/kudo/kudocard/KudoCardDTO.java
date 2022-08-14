package br.com.vemser.retrocards.dto.kudo.kudocard;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class KudoCardDTO {

    @Schema(description = "Id Kudo card.")
    @NotNull
    private Integer idKudoCard;

    @Schema(description = "Título da Kudo card.")
    @NotBlank
    @Size(min = 3, max = 60)
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

    @Schema(description = "Nome do Kudo box que o Kudo card foi criado.")
    private String kudoBox;
}
