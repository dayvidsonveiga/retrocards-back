package br.com.vemser.retrocards.dto.email;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class EmailCreateDTO {

    @Schema(description = "Receiver")
    @NotNull
    private List<String> receiver;
}
