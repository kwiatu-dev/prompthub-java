package com.springboot.prompthub.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Date;

@Converter
public class SoftDeleteTypeConverter implements AttributeConverter<Boolean, String> {
    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return attribute ? "active" : "inactive";
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return dbData.equals("active");
    }
}
