package br.com.vemser.retrocards.dto.itemRetrospective;

import br.com.vemser.retrocards.enums.ItemType;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ItemRetrospectiveCreateDTO {

    @Schema(description = "Id da retrospectiva")
    @NotNull
    private Integer idRetrospective;

    @Schema(description = "Tipo do item de avaliação.")
    @Hidden
    private ItemType type;

    @Schema(description = "Título do item.")
    @NotBlank
    @Size(min = 3, max = 60)
    private String title;

    @Schema(description = "Descrição da avaliação.")
    @NotBlank
    private String description;
}
