package com.danielme.jakartaee.jpa.converters;

import com.danielme.jakartaee.jpa.entities.Geometry;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ShapeAttributeConverter implements AttributeConverter<Geometry.Shape, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Geometry.Shape attribute) {
        return attribute.id();
    }

    @Override
    public Geometry.Shape convertToEntityAttribute(Integer dbData) {
        return Geometry.Shape.byId(dbData);
    }
}
