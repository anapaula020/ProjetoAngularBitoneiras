package mssaat.org.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EscritorNovelDTO(
        @NotBlank(message = "O nome é obrigatório") @Size(min = 3, message = "Nome pequeno demais.") @Size(max = 40, message = "Nome grande demais") String nome,
        @NotNull(message = "Ano de nascimento é obrigatório") @Min(value = 0) Integer anoNascimento,
        @NotBlank(message = "A nacionalidade é obrigatória") @Size(min = 2, message = "A nacionalidade está pequeno demais") @Size(max = 30, message = "A nacionalidade está grande demais") String nacionalidade,
        @NotNull(message = "O sexo é obrigatório") Integer sexo) {
}