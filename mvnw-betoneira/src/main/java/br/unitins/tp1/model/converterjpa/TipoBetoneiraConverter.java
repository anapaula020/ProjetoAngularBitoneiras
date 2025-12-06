package br.unitins.tp1.model.converterjpa;

import br.unitins.tp1.model.EnumTipoBetoneira;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoBetoneiraConverter implements AttributeConverter<EnumTipoBetoneira, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumTipoBetoneira tipoBetoneira) {
        return tipoBetoneira == null ? null : tipoBetoneira.getId();
    }

    @Override
    public EnumTipoBetoneira convertToEntityAttribute(Integer dbData) {
        return EnumTipoBetoneira.valueOf(dbData);
    }
}