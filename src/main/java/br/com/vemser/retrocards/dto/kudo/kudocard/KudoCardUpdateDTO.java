package br.com.vemser.retrocards.dto.kudo.kudocard;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class KudoCardUpdateDTO {

    @Schema(description = "Id Kudo card.")
    @Hidden
    private Integer idKudoCard;

    @Schema(description = "Id usuário que criou o card.")
    @Hidden
    private Integer idCreator;

    @Schema(description = "Título da Kudo card.")
    private String title;

    @Schema(description = "Data em que foi criada a Kudo card.")
    private LocalDateTime createDate;

    @Schema(description = "Nome do usuário que submeteu a Kudo card.")
    private String sender;

    @Schema(description = "Nome do usuário que recebeu a Kudo card.")
    private String receiver;

    @Schema(description = "Descrição do Kudo card.")
    private String description;
}
