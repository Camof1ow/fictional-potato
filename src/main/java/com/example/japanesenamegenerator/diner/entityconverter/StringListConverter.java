package com.example.japanesenamegenerator.diner.entityconverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@Converter(autoApply = true)
public class StringListConverter implements AttributeConverter<List<String>, String> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> dataList) {
        if (dataList == null) {
            return null;
        }
        try {
            return mapper.writeValueAsString(dataList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert List<String> to JSON string", e);
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String data) {
        if (data == null || data.isEmpty()) {
            return Collections.emptyList();
        }
        try {
            return mapper.readValue(data, new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON string to List<String>", e);
        }
    }
}
