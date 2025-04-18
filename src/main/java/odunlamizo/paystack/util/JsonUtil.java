package odunlamizo.paystack.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.OutputStream;

public final class JsonUtil {

    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper().findAndRegisterModules();
    }

    private JsonUtil() {}

    public static void writeValue(OutputStream out, Object value) throws IOException {
        MAPPER.writeValue(out, value);
    }

    public static <T> String toJson(T object) throws JsonProcessingException {
        return MAPPER.writeValueAsString(object);
    }

    public static <T> T toValue(String jsonData, Class<T> valueType)
            throws JsonMappingException, JsonProcessingException {
        return MAPPER.readValue(jsonData, valueType);
    }

    public static <T> T toValue(String jsonData, TypeReference<T> valueTypeRef)
            throws JsonProcessingException {
        return MAPPER.readValue(jsonData, valueTypeRef);
    }
}
