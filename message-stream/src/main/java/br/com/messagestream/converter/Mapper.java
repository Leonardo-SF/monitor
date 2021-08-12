package converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
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
