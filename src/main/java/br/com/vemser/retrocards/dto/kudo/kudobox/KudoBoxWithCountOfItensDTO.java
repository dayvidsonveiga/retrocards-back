package br.com.vemser.retrocards.dto.kudo.kudobox;

import br.com.vemser.retrocards.enums.KudoStatus;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class KudoBoxWithCountOfItensDTO {

    @Schema(description = "Id Kudo box.")
    private Integer idKudoBox;

    @Schema(description = "Título da Kudo box.")
    private String title;

    @Schema(description = "Data de término da Kudo box.")
    private LocalDateTime endDate;

    @Schema(description = "Status da Kudo box.")
    private KudoStatus status;

    @Schema(description = "Quantidade de itens cadastrados na kudobox.")
    private Integer numberOfItens;
}
