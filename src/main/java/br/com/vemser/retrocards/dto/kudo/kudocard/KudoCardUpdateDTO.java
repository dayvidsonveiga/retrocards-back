package br.com.vemser.retrocards.dto.kudo.kudocard;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class KudoCardUpdateDTO {

    @Schema(description = "Título da Kudo card.")
    private String title;

    @Schema(description = "Descrição do Kudo card.")
    private String description;
}
