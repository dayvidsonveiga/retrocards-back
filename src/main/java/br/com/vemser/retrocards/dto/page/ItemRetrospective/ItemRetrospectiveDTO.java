package br.com.vemser.retrocards.dto.page.ItemRetrospective;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ItemRetrospectiveDTO {

    @Schema(description = "Id item retrospectiva.")
    @NotNull
    private Integer idItemRetrospective;

    @Schema(description = "Tipo do item de avaliação.")
    @NotBlank
    private String type;

    @Schema(description = "Título do item.")
    @NotBlank
    private String title;

    @Schema(description = "Descrição da avaliação.")
    @NotBlank
    private String description;
}
