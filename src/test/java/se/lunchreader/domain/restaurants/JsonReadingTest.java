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
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.List;

public class JsonReadingTest {
    private HttpClient httpClient = HttpClient.newBuilder().build();
    private LunchComponent component = DaggerLunchComponent.create();
    private JacksonBodyHandlers bodyHandlers = new JacksonBodyHandlers(new ObjectMapper());

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
        var bodyHandler = bodyHandlers.handlerFor(new TypeReference<List<RestaurantsResponse>>() {});
        var response = httpClient.send(request, bodyHandler);
        System.out.println(response.body());
    }
}
