package se.lunchreader.domain.restaurants.mynchen;

import se.lunchreader.domain.data.WeekDays;
import se.lunchreader.domain.data.Meal;
import se.lunchreader.domain.data.Restaurant;
import se.lunchreader.infrastructure.client.PdfParser;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URI;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Singleton
public class Mynchen implements Restaurant {
    private static final String URL = "https://static1.squarespace.com/static/578916fc725e25c4aa70ea79/t/5ccfb97cec212dcb44fe039b/1557117308361/Veckans+Lunch.pdf";
    private static final Pattern REGEX = Pattern.compile("(\\D*) (\\d*):-");
    private static final List<String> SUFFIXES = Stream.concat(WeekDays.SWEDISH_WEEK_DAYS.stream(), Stream.of("Hela veckan", "Sushi")).collect(toList());
    private final PdfParser pdfParser;

    @Inject
    public Mynchen(PdfParser pdfParser) {
        this.pdfParser = pdfParser;
    }

    @Override
    public List<Meal> onMenu(DayOfWeek dayOfWeek) {
        var dayInSwedish = WeekDays.WEEK_DAYS_TO_SWEDISH.get(dayOfWeek);
        var lines = pdfParser.parsePdf(URI.create(URL));
        var linesForDay = findLinesForSection(lines, dayInSwedish);
        var linesForAlwaysOnMenu = findLinesForSection(lines, "Hela veckan");

        return Stream.concat(linesForDay, linesForAlwaysOnMenu)
                .flatMap(line -> parseMeal(line).stream())
                .collect(toList());
    }

    @Override
    public String name() {
        return "Mynchen";
    }

    private Stream<String> findLinesForSection(List<String> lines, String section) {
        return lines.stream()
                .dropWhile(line -> !line.equals(section))
                .dropWhile(line -> line.equals(section))
                .takeWhile(line -> !SUFFIXES.contains(line));
    }

    private Optional<Meal> parseMeal(String line) {
        var matcher = REGEX.matcher(line);
        if (!matcher.matches() || matcher.groupCount() != 2) {
            return Optional.empty();
        }

        var desc = matcher.group(1);
        var price = Double.parseDouble(matcher.group(2));
        return Optional.of(Meal.create(desc, price));
    }

}