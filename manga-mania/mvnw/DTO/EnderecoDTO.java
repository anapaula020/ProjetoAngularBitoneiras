package mssaat.org.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EnderecoDTO(
    @Size(min = 3, message = "Rua pequena demais.") @Size(max = 50, message = "Rua grande demais") @NotBlank(message = "A rua é obrigatória") String rua,
    @NotBlank(message = "O número é obrigatório") String numero,
    @Size(min = 8, message = "CEP pequeno demais.") @Size(max = 9, message = "CEP grande demais") @NotBlank(message = "O CEP é obrigatório") String cep,
    @Size(min = 2, message = "Cidade pequena demais.") @Size(max = 50, message = "Cidade grande demais") @NotBlank(message = "A cidade é obrigatória") String cidade,
    @Size(min = 2, message = "Estado pequeno demais.") @Size(max = 2, message = "Estado grande demais") @NotBlank(message = "O estado é obrigatório") String estado
) {
}