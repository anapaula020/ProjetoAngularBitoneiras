package mssaat.org.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PixDTO (
    @NotNull(message = "O id do pedido é obrigatório")
    Long idPedido,
    @NotBlank(message = "O cpf do PIX é obrigatório")
    String cpf,
    @NotNull(message = "O valor é obrigatório")
    Double valor
){
    
}