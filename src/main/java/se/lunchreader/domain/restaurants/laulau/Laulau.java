package se.lunchreader.domain.restaurants.laulau;

import se.lunchreader.domain.data.Meal;
import se.lunchreader.domain.data.Restaurant;
import se.lunchreader.infrastructure.client.PdfParser;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URI;
import java.time.DayOfWeek;
import java.util.List;

@Singleton
public class Laulau implements Restaurant {
    private final LauLauMenuParser menuParser;
    private final PdfParser menuReader;
    private final URI url;

    @Inject
    public Laulau(PdfParser menuReader, LauLauMenuParser menuParser, URI url) {
        this.menuReader = menuReader;
        this.menuParser = menuParser;
        this.url = url;
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
