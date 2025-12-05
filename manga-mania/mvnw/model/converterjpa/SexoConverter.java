package mssaat.org.model.converterjpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import mssaat.org.model.Sexo;

@Converter(autoApply = true)
public class SexoConverter implements AttributeConverter<Sexo, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Sexo sexo) {
        return sexo.getId();
    }

    @Override
    public Sexo convertToEntityAttribute(Integer sexo) {
        return Sexo.valueOf(sexo);
    }
}