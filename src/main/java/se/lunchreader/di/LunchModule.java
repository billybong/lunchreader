package se.lunchreader.di;

import dagger.Module;
import dagger.Provides;
import se.lunchreader.domain.data.Restaurant;
import se.lunchreader.domain.restaurants.laulau.Laulau;
import se.lunchreader.domain.restaurants.mynchen.Mynchen;
import se.lunchreader.infrastructure.client.IPdfParser;
import se.lunchreader.infrastructure.client.PdfParser;
import se.lunchreader.infrastructure.config.Config;

import javax.inject.Singleton;
import java.net.URI;
import java.util.List;

import static java.util.Arrays.asList;

@Module
public class LunchModule {

    @Provides
    @Singleton
    public URI uri(Config config){
        return config.lauLauUri();
    }

    @Provides
    @Singleton
    public List<Restaurant> restaurants(Mynchen mynchen, Laulau laulau){
        return asList(
                mynchen
        );
    }

    @Provides
    @Singleton
    public IPdfParser pdfParser(){
        return new PdfParser();
    }
}
