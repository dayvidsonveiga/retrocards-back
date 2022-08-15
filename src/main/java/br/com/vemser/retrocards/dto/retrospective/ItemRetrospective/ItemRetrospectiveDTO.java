package br.com.vemser.retrocards.dto.retrospective.ItemRetrospective;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ItemRetrospectiveDTO {

    @Schema(description = "Id item retrospectiva.")
    private Integer idItemRetrospective;

    @Schema(description = "Tipo do item de avaliação.")
    private String type;

    @Schema(description = "Título do item.")
    private String title;

    @Schema(description = "Descrição da avaliação.")
    private String description;

    @Schema(description = "Retrospectiva do item")
    private String retrospective;

}
