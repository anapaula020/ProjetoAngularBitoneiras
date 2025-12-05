package mssaat.org.model.converterjpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import mssaat.org.model.GeneroManga;

@Converter(autoApply = true)
public class GeneroMangaConverter implements AttributeConverter<GeneroManga, Integer>{
  
    @Override
    public Integer convertToDatabaseColumn(GeneroManga genero) {
        return genero.getId();
    }

    @Override
    public GeneroManga convertToEntityAttribute(Integer id) {
        return GeneroManga.value(id);
    }
}