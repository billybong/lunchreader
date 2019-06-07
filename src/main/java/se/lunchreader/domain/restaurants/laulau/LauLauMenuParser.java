package se.lunchreader.domain.restaurants.laulau;

import se.lunchreader.domain.data.Meal;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class LauLauMenuParser {

    @Inject
    public LauLauMenuParser() {
    }

    List<Meal> parseMenu(List<String> text) {
        System.out.println(text);
        return List.of();
    }
}
