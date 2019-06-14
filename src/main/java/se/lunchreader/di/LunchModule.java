package se.lunchreader.di;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import se.lunchreader.domain.data.Restaurant;
import se.lunchreader.domain.restaurants.laulau.Laulau;
import se.lunchreader.domain.restaurants.mynchen.Mynchen;
import se.lunchreader.infrastructure.client.IPdfParser;
import se.lunchreader.infrastructure.client.PdfParser;

import javax.inject.Singleton;
import java.util.List;

import static java.util.Arrays.asList;

@Module
public abstract class LunchModule {

    @Provides
    @Singleton
    public static List<Restaurant> restaurants(Mynchen mynchen, Laulau laulau){
        return asList(
                mynchen
        );
    }

    @Binds
    abstract public IPdfParser pdfParser(PdfParser pdfParser);
}
