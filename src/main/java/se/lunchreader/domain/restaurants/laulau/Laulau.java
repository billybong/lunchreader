package se.lunchreader.domain.restaurants.laulau;

import se.lunchreader.domain.data.Meal;
import se.lunchreader.domain.data.Restaurant;
import se.lunchreader.infrastructure.client.IPdfParser;
import se.lunchreader.infrastructure.config.Config;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URI;
import java.time.DayOfWeek;
import java.util.List;

@Singleton
public class Laulau implements Restaurant {
    private final LauLauMenuParser menuParser;
    private final IPdfParser menuReader;
    private final URI url;

    @Inject
    public Laulau(IPdfParser menuReader, LauLauMenuParser menuParser, Config config) {
        this.menuReader = menuReader;
        this.menuParser = menuParser;
        url = config.lauLauUri();
    }

    @Override
    public List<Meal> onMenu(DayOfWeek dayOfWeek) {
        var text = menuReader.parsePdf(url);
        return menuParser.parseMenu(text);
    }

    @Override
    public String name() {
        return "LauLau";
    }
}
