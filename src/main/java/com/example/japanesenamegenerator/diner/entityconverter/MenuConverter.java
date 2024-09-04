package com.example.japanesenamegenerator.diner.entityconverter;

import com.example.japanesenamegenerator.diner.domain.DinerDetail.Menu;
import com.google.gson.Gson;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.modelmapper.TypeToken;

import java.util.List;

@Converter
public class MenuConverter implements AttributeConverter<List<Menu>, String> {

    @Override
    public String convertToDatabaseColumn(List<Menu> attribute) {
        return new Gson().toJson(attribute);
    }

    @Override
    public List<Menu> convertToEntityAttribute(String dbData) {
        return new Gson().fromJson(dbData, new TypeToken<List<Menu>>(){}.getType());
    }
}