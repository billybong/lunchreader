package se.lunchreader.domain.restaurants;

import org.junit.jupiter.api.Test;
import se.lunchreader.di.DaggerLunchComponent;
import se.lunchreader.di.LunchComponent;
import se.lunchreader.di.LunchModule;
import se.lunchreader.infrastructure.client.IPdfParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

public class IntegrationTest {
    private HttpClient httpClient = HttpClient.newBuilder().build();
    private LunchComponent component = DaggerLunchComponent.builder().lunchModule(new LunchModule(){
        @Override public IPdfParser pdfParser() {
            return (url) -> List.of("");
        }
    }).build();

    @Test
    void mockingPdfParser() throws IOException, InterruptedException {
        component.server().start();
        var request = HttpRequest.newBuilder(URI.create("http://localhost:7090/lunch")).build();
        var response = httpClient.send(request, BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
