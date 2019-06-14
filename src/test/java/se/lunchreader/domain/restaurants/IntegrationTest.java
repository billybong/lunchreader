package se.lunchreader.domain.restaurants;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import org.junit.jupiter.api.Test;
import se.lunchreader.di.DaggerLunchComponent;
import se.lunchreader.di.LunchComponent;
import se.lunchreader.di.LunchModule;
import se.lunchreader.domain.data.Restaurant;
import se.lunchreader.domain.restaurants.mynchen.Mynchen;
import se.lunchreader.infrastructure.client.IPdfParser;
import se.lunchreader.infrastructure.client.PdfParser;

import javax.inject.Singleton;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

public class IntegrationTest {
    private HttpClient httpClient = HttpClient.newBuilder().build();
    private LunchComponent component = DaggerIntegrationTest_LunchTestComponent.builder().build();

    @Test
    void mockingPdfParser() throws IOException, InterruptedException {
        component.server().start();
        var request = HttpRequest.newBuilder(URI.create("http://localhost:7090/lunch")).build();
        var response = httpClient.send(request, BodyHandlers.ofString());
        System.out.println(response.body());
    }


    @Singleton
    @Component(modules = LunchTestModule.class)
    public interface LunchTestComponent extends LunchComponent {
    }

    @Module
    public static abstract class LunchTestModule {

        @Provides
        @Singleton
        static List<Restaurant> providesListOfRestaurants(Mynchen mynchen){
            return List.of(mynchen);
        }

        @Provides
        @Singleton
        static IPdfParser providesPdfParser() {
            return (ignored) -> List.of("");
        }
    }
}
