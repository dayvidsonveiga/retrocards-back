package br.com.vemser.retrocards.dto.retrospective.ItemRetrospective;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ItemRetrospectiveUpdateDTO {

    @Schema(description = "Id da retrospectiva")
    private Integer idRetrospective;

    @Schema(description = "Tipo do item de avaliação.")
    private String type;

    @Schema(description = "Título do item.")
    private String title;

    @Schema(description = "Descrição da avaliação.")
    private String description;
}
