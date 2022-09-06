package br.com.vemser.retrocards.dto.itemRetrospective;

import br.com.vemser.retrocards.enums.ItemType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ItemRetrospectiveDTO {

    @Schema(description = "Id item retrospectiva.")
    private Integer idItemRetrospective;

    @Schema(description = "Id da retrospectiva")
    private Integer idRetrospective;

    @Schema(description = "Tipo do item de avaliação.")
    private ItemType type;

    @Schema(description = "Título do item.")
    private String title;

    @Schema(description = "Descrição da avaliação.")
    private String description;
}
