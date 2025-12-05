package mssaat.org.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AvaliacaoDTO(
        Long idUsuario,
        Long idManga, 
        Integer estrelas, 
        @NotBlank(message = "Conteúdo do comentário não pode estar vazio") @Size(min = 5, message = "Comentário pequeno demais.") @Size(max = 1000, message = "Comentário grande demais.") String comentario) {
}