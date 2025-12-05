package mssaat.org.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AutorMangaDTO(
        @Size(min = 3, message = "Nome pequeno demais.") @Size(max = 40, message = "Nome grande demais") @NotBlank(message = "O nome é obrigatório") String nome,
        @Min(value = 0) Integer anoNascimento,
        @Size(min = 2, message = "A nacionalidade está pequeno demais.") @Size(max = 30, message = "A nacionalidade está pequeno grande.") @NotBlank(message = "A nacionalidade é obrigatória") String nacionalidade,
        @NotNull(message = "O sexo é obrigatório") Integer sexo) {
}
