package com.example.taskApp.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import javax.print.attribute.Attribute;
import java.util.HashMap;
import java.util.Map;


@Converter

public class ProductPropertiesConverter implements AttributeConverter<Map<String, Object>,String> {

    public ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public String convertToDatabaseColumn(Map<String, Object> stringObjectMapProperties) {
        if(stringObjectMapProperties==null) {
            return "{}";
        }

        try {
            return objectMapper.writeValueAsString(stringObjectMapProperties);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String dBData) {
        if(dBData==null){
            return new HashMap<>();
        }
        try {
            return objectMapper.readValue(dBData, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
