package mssaat.org.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NovelDTO(
        @Size(min = 3, max = 40, message = "O nome deve ter entre 3 e 40 caracteres") @NotBlank(message = "O nome é obrigatório") String nome, 
        String imageUrl,  
        @Size(min = 10, max = 1000, message = "A sinopse deve ter no mínimo 30 caracteres e máximo 1000 caracteres") @NotBlank(message = "A sinopse é obrigatória") String sinopse, 
        @Min(value = 0, message = "O genero precisa ser maior do que 0.") @NotNull(message = "O gênero é obrigatório") int genero, 
        @Min(value = 0, message = "O idAutor precisa ser maior do que 0.") @NotNull(message = "O id do autor é obrigatório") Long idAutor, 
        @Min(value = 0, message = "O ano de lançamento precisa ser maior do que 0.") @NotNull(message = "O ano de lançamento é obrigatório") int lancamento, 
        @Min(value = 0, message = "O preco precisa ser maior do que 0.") @NotNull(message = "O preço é obrigatório") Double preco, 
        @Min(value = 0, message = "O estoque precisa ser maior do que 0.") @NotNull(message = "O estoque é obrigatório") int estoque, 
        @Min(value = 0, message = "O paginas precisa ser maior do que 0.") @NotNull(message = "A quantidade de páginas é obrigatória") int paginas, 
        @Min(value = 0, message = "O capitulos precisa ser maior do que 0.") @NotNull(message = "O número de capítulos é obrigatório") int capitulos) { 
}

    
    
    
    
    
    
    
    
    
    