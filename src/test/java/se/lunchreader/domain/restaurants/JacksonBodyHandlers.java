package se.lunchreader.domain.restaurants;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.http.HttpResponse;

public class JacksonBodyHandlers {
    private final ObjectMapper objectMapper;

    public JacksonBodyHandlers(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> HttpResponse.BodyHandler<T> handlerFor(TypeReference<T> typeReference){
        return responseInfo -> mapping((bytes) -> objectMapper.readValue(bytes, typeReference));
    }

    public <T> HttpResponse.BodyHandler<T> handlerFor(Class<T> clazz){
        return responseInfo -> mapping((bytes) -> objectMapper.readValue(bytes, clazz));
    }

    private <T> HttpResponse.BodySubscriber<T> mapping(ExceptionalFunction<byte[], T> exceptionalFunction) {
        return HttpResponse.BodySubscribers.mapping(HttpResponse.BodySubscribers.ofByteArray(), (byteArray) -> {
            try {
                return exceptionalFunction.apply(byteArray);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
    }

    @FunctionalInterface
    interface ExceptionalFunction<V, T> {
        T apply(V value) throws IOException;
    }
}
