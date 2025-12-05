package mssaat.org.DTO;

import mssaat.org.model.Avaliacao;
import mssaat.org.model.Manga;
import mssaat.org.model.Usuario;

public record AvaliacaoResponseDTO(Long id, Usuario user, Manga manga, String comentario, Integer Estrelas) {
    public static AvaliacaoResponseDTO valueOf(Avaliacao avaliacao) {
        if(avaliacao == null) return null;
        return new AvaliacaoResponseDTO(avaliacao.getId(), avaliacao.getUsuario(), avaliacao.getManga(), avaliacao.getComentario(), avaliacao.getEstrelas());
    }
}