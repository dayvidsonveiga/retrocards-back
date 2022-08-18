package br.com.vemser.retrocards.dto.kudo.kudocard;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class KudoCardCreateDTO {

    @Schema(description = "Id do Kudo box")
    @NotNull
    private Integer idKudoBox;

    @Schema(description = "Título da Kudo card.")
    @NotBlank
    @Size(min = 3, max = 60)
    private String title;

    @Schema(description = "Remetente privado ou público")
    private Boolean anonymous;

    @Schema(description = "Nome do usuário que recebeu a Kudo card.")
    @NotBlank
    private String receiver;

    @Schema(description = "Descrição do Kudo card.")
    @NotBlank
    private String description;
}
