package br.com.vemser.retrocards.dto.kudo.kudocard;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class KudoCardCreateDTO {

    @Schema(description = "Id do Kudo box")
    @NotNull(message = "É necessário informar o ID da kudo box que deseja associar o kudo card!")
    private Integer idKudoBox;

    @Schema(description = "Título da Kudo card.")
    @NotBlank(message = "O título do kudo card não pode ser vazio/nulo!")
    @Size(min = 3, max = 60)
    private String title;

    @Schema(description = "Remetente privado ou público")
    private Boolean anonymous;

    @Schema(description = "Nome do usuário que recebeu a Kudo card.")
    @NotBlank(message = "O nome do destinatário não pode ser vazio/nulo!")
    private String receiver;

    @Schema(description = "Descrição do Kudo card.")
    @NotBlank(message = "A descrição do kudo card não pode ser vazia/nula!")
    private String description;
}
