package com.springboot.prompthub.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.List;

@Converter
public class MessageConverter implements AttributeConverter<List<Message>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Message> messages) {
        try {
            return objectMapper.writeValueAsString(messages);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    @Override
    public List<Message> convertToEntityAttribute(String dbMessages) {
        try {
            return objectMapper.readValue(
                    dbMessages,
                    objectMapper
                            .getTypeFactory()
                            .constructCollectionType(List.class, Message.class));
        } catch (IOException e) {
            return null;
        }
    }
}
