package mssaat.org.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CartaoDTO(
    @NotNull(message = "O id do pedido é obrigatório")
    Long idPedido,
    @NotBlank(message = "O número do cartão é obrigatório")
    String numero,
    @NotBlank(message = "O nome do titular é obrigatório")
    String nome,
    
    @NotBlank(message = "O CVV do cartão é obrigatório")
    String cvv,
    @NotNull(message = "O limite do cartão é obrigatório")
    Double limite
    
) {
	
}