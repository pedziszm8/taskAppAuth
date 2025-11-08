package com.example.taskApp.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.*;

@Component                                                          //przekazanie
public class ProductStringToMapConverter {
    public Map<String,Object> convert(String user_input){
        String[] splited = user_input.split(";");
        Map<String, Object> map = new HashMap<>();
        for (String word: splited) {
            String[] parts = word.split(":");
            String key = parts[0];
            String value = parts[1];

            map.put(key, value);

        }

        return map;
    }



    public Map<String, Object> alternativeTree(String user_input) {
        String[] splited = user_input.split("\\|");
        Map<String, Object> map = new HashMap<>();


        for (String group :
                splited) {
            //  String[] strings = group.split(":", 2);
            int idx = group.indexOf(":");
            String key = group.substring(0, idx);     // a
            String right = group.substring(idx + 1);    // b=c

            if (right.contains(":")==false) {
                map.put(key, right);

            }

            else {
                String[] strings = right.split(":", 2);
                for (String undergroup :
                        strings) {
                    //  String[] strings = group.split(":", 2);
                                                               // int idx = group.indexOf("=");
                    String key2 = group.substring(0, idx);     // a
                    String right2 = group.substring(idx + 1);    // b=c
                    if (right.contains(":")==false) {
                        map.put(key, right);

                    }
                }

            }

        }
        return map;
    }








    public static List<String> splitIgnoringQuotes(String text) {
        List<String> parts = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
            }

            if (c == ';' && !inQuotes) {
                parts.add(current.toString().trim());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }

        if (current.length() > 0) {
            parts.add(current.toString().trim());
        }

        return parts;
    }


    public static Map<String, Object> parseProperties(String input) {
        Map<String, Object> map = new LinkedHashMap<>();

        for (String part : splitIgnoringQuotes(input)) {
            if (!part.contains(":")) continue;

            String[] kv = part.split(":", 2);
            String key = kv[0].trim();
            String value = kv[1].trim();

            if (value.startsWith("\"") && value.endsWith("\"")) {
                value = value.substring(1, value.length() - 1);
            }

            // Sprawdzamy czy wartość ma kolejne poziomy
            if (value.contains(":")) {
                map.put(key, parseProperties(value));
            } else {
                map.put(key, value);
            }
        }

        return map;
    }



}


// wymiary:"serokosc:100; wysokosc:300;";        waga:10;
/*
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
}*/