package br.unitins.tp1.model.converterjpa;

import br.unitins.tp1.model.Regiao; // Correct import for the new Regiao enum
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RegiaoConverter implements AttributeConverter<Regiao, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Regiao regiao) {
        if (regiao == null) {
            return null;
        }
        return regiao.getValue();
    }

    @Override
    public Regiao convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return Regiao.valueOf(dbData);
    }
}