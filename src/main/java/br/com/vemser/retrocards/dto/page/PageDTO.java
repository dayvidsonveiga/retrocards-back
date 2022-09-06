package br.com.vemser.retrocards.dto.page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO<T> {

    @Schema(description = "Número total de elementos na página")
    private Long totalElements;

    @Schema(description = "Número total de páginas")
    private Integer totalPages;

    @Schema(description = "Número da página")
    private Integer page;

    @Schema(description = "Tamanho total de páginas")
    private Integer size;

    @Schema(description = "Lista de elementos")
    private List<T> content;
}
