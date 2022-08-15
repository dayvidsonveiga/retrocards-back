package br.com.vemser.retrocards.dto.retrospective.ItemRetrospective;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ItemRetrospectiveUpdateDTO {

    @Schema(description = "Id da retrospectiva")
    @NotNull
    private Integer idRetrospective;

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
