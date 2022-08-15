package br.com.vemser.retrocards.dto.kudo.kudocard;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class KudoCardDTO {

    @Schema(description = "Id Kudo card.")
    private Integer idKudoCard;

    @Schema(description = "Id usuário que criou o card.")
    private Integer idCreator;

    @Schema(description = "Título da Kudo card.")
    private String title;

    @Schema(description = "Data em que foi criada a Kudo card.")
    private LocalDateTime createDate;

    @Schema(description = "Nome do usuário que submeteu a Kudo card.")
    private String sender;

    @Schema(description = "Nome do usuário que recebeu a Kudo card.")
    private String receiver;
}
