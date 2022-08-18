package br.com.vemser.retrocards.dto.email;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class EmailCreateDTO {

    @Schema(description = "Receiver")
    private List<String> receiver;

    @Schema(description = "Subject")
    @NotBlank
    private String subject;
}
