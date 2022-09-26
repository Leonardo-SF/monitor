package br.com.messagestream.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.function.BiFunction;

public class Mapper {

    private ObjectMapper objectMapper;

    public Mapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public final BiFunction<String, Class<?>, ?> convert = (String message, Class<?> clazz) -> {
        try {
            return objectMapper.readValue(message, clazz);
        } catch (JsonProcessingException e) {
            return null;
        }
    };
}
