package mssaat.org.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MangaDTO(
        @Size(min = 3, max = 80, message = "O nome deve ter entre 3 e 80 caracteres") @NotBlank(message = "O nome é obrigatório") String nome,
        String imageUrl, 
        @Size(min = 10, max = 1000, message = "A sinopse deve ter no mínimo 30 caracteres e máximo 1000.") @NotBlank(message = "A sinopse é obrigatória") String sinopse,
        @NotNull(message = "O gênero é obrigatório") int genero,
        @NotNull(message = "O id do autor é obrigatório") Long idAutor,
        @Min(value = 0, message = "O ano de lançamento precisa ser maior do que 0.") @NotNull(message = "O ano de lançamento é obrigatório") int lancamento,
        @Size(min = 2, max = 12, message = "O nome deve ter entre 3 e 40 caracteres") @NotBlank(message = "A cor é obrigatória") String color,
        @Min(value = 0, message = "O preço precisa ser maior do que 0.") @NotNull(message = "O preço é obrigatório") Double preco,
        @Min(value = 0, message = "O estoque precisa ser maior do que 0.") @NotNull(message = "O estoque é obrigatório") int estoque,
        @Min(value = 0, message = "As paginas precisa ser maior do que 0.") @NotNull(message = "A quantidade de páginas é obrigatória") int paginas) {
}