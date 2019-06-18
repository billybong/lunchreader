package se.lunchreader.domain.restaurants;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.lunchreader.di.DaggerLunchComponent;
import se.lunchreader.di.LunchComponent;
import se.lunchreader.domain.service.RestaurantsResponse;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class JsonReadingTest {
    private HttpClient httpClient = HttpClient.newBuilder().build();
    private LunchComponent component = DaggerLunchComponent.create();

    @BeforeEach
    public void before(){
        component.server().start();
    }

    @AfterEach
    public void teardown() {
        component.server().close();
    }

    @Test
    void mockingPdfParser() throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder(URI.create("http://localhost:7090/lunch")).build();
        var bodyHandler = JsonTypeRefBodyHandler.jsonBodyHandler(new TypeReference<List<RestaurantsResponse>>() {});
        var response = httpClient.send(request, bodyHandler);
        System.out.println(response.body());
    }

    public static class JsonBodyHandler<T> implements HttpResponse.BodyHandler<T> {
        private final ObjectMapper objectMapper;
        private final Class<T> type;

        public static <T> JsonBodyHandler<T> jsonBodyHandler(final Class<T> type) {
            return jsonBodyHandler(new ObjectMapper(), type);
        }

        public static <T> JsonBodyHandler<T> jsonBodyHandler(final ObjectMapper jsonb,
                                                             final Class<T> type) {
            return new JsonBodyHandler<>(jsonb, type);
        }

        private JsonBodyHandler(ObjectMapper objectMapper, Class<T> type) {
            this.objectMapper = objectMapper;
            this.type = type;
        }

        @Override
        public HttpResponse.BodySubscriber<T> apply(
                final HttpResponse.ResponseInfo responseInfo) {
            return HttpResponse.BodySubscribers.mapping(HttpResponse.BodySubscribers.ofInputStream(),
                    byteArray -> {
                        try {
                            return this.objectMapper.readValue(byteArray, this.type);
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    });
        }
    }

    public static class JsonTypeRefBodyHandler<T> implements HttpResponse.BodyHandler<T> {
        private final ObjectMapper objectMapper;
        private final TypeReference<T> typeRef;

        public static <T> JsonTypeRefBodyHandler<T> jsonBodyHandler(final TypeReference<T> typeRef) {
            return jsonBodyHandler(new ObjectMapper(), typeRef);
        }

        public static <T> JsonTypeRefBodyHandler<T> jsonBodyHandler(final ObjectMapper jsonb,
                                                             final TypeReference<T> typeRef) {
            return new JsonTypeRefBodyHandler<>(jsonb, typeRef);
        }

        private JsonTypeRefBodyHandler(ObjectMapper objectMapper, TypeReference<T> typeRef) {
            this.objectMapper = objectMapper;
            this.typeRef = typeRef;
        }

        @Override
        public HttpResponse.BodySubscriber<T> apply(
                final HttpResponse.ResponseInfo responseInfo) {
            return HttpResponse.BodySubscribers.mapping(HttpResponse.BodySubscribers.ofByteArray(),
                    byteArray -> {
                        try {
                            return this.objectMapper.readValue(byteArray, this.typeRef);
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    });
        }
    }
}
