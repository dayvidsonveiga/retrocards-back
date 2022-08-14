package br.com.vemser.retrocards.dto.retrospective;

import br.com.vemser.retrocards.controller.enums.ItemType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ItemRetrospectiveCreateDTO {

    @Schema(description = "Tipo do item de avaliação.")
    @NotNull
    private ItemType type;

    @Schema(description = "Título do item.")
    @NotBlank
    @Size(min = 3, max = 60)
    private String title;

    @Schema(description = "Descrição da avaliação.")
    @NotBlank
    private String description;
}
