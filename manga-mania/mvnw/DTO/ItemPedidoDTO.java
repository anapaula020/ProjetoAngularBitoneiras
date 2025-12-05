package mssaat.org.DTO;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.Min;
import mssaat.org.model.Manga;

public record ItemPedidoDTO(
        @NotNull Long idManga,
        @Min(value = 1, message = "Preco precisa ser maior do que 0.") Double preco,
        @Min(value = 1, message = "Desconto precisa ser maior do que 0.") Double desconto,
        @Min(value = 1, message = "Quantidade precisa ser maior do que 0.") Integer quantidade) {
}