package br.com.vemser.retrocards.dto.itemRetrospective;

import br.com.vemser.retrocards.enums.ItemType;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ItemRetrospectiveUpdateDTO {

    @Schema(description = "Id da retrospectiva")
    private Integer idRetrospective;

    @Schema(description = "Tipo da avaliação.")
    @Hidden
    private ItemType type;

    @Schema(description = "Título do item.")
    private String title;

    @Schema(description = "Descrição da avaliação.")
    private String description;
}