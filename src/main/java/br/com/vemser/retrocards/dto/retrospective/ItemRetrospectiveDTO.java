package br.com.vemser.retrocards.dto.retrospective;

import br.com.vemser.retrocards.enums.ItemType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ItemRetrospectiveDTO {

    @Schema(description = "Tipo do item de avaliação.")
    private ItemType type;

    @Schema(description = "Título do item.")
    private String title;

    @Schema(description = "Descrição da avaliação.")
    private String description;
}
