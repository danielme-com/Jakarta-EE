package com.danielme.jakartaee.jpa.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BooleanAttributeConverter implements AttributeConverter<Boolean, Character> {

    private static final Character TRUE_VALUE = 'S';
    private static final Character FALSE_VALUE = 'N';

    @Override
    public Character convertToDatabaseColumn(Boolean attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute ? TRUE_VALUE : FALSE_VALUE;
    }

    @Override
    public Boolean convertToEntityAttribute(Character dbData) {
       if (dbData == null) {
           return null;
       }
       return dbData.equals(TRUE_VALUE);
    }

}
