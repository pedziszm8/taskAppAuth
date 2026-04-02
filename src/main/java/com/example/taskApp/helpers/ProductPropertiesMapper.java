package com.example.taskApp.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;


@Component
public class ProductPropertiesMapper {

    private final ObjectMapper mapper = new ObjectMapper();

    public String mapToString(Map<String, Object> properties) {
        try {
            if (properties == null) return "";
            return mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(properties);
        } catch (Exception e) {
            return "";
        }
    }


    public String mapToString2(Map<String, Object> properties) {
        String collectRawString = properties.entrySet().stream()
                .map(element -> element.getKey() + ":" + element.getValue())
                .collect(Collectors.joining(";"));
        return collectRawString;

    }
}


