package se.lunchreader.domain.restaurants;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import se.lunchreader.domain.data.WeekDays;
import se.lunchreader.domain.data.Meal;
import se.lunchreader.domain.data.Restaurant;

import java.io.IOException;
import java.net.URI;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;

public class Mynchen implements Restaurant {
    //private static final String URL = "https://static1.squarespace.com/static/578916fc725e25c4aa70ea79/t/5ba0a67c575d1ff43f1db1d7/1537255036662/veckanslunch.pdf";
    private static final String URL = "file:///home/billy/dev/projects/lunchreader/src/main/resources/veckanslunch.pdf";
    private static final Pattern REGEX = Pattern.compile("(\\D*) (\\d*):-");
    public static final List<String> SUFFIXES = Stream.concat(WeekDays.SWEDISH_WEEK_DAYS.stream(), Stream.of("Hela veckan", "Sushi")).collect(toList());

    @Override
    public List<Meal> onMenu(DayOfWeek dayOfWeek) {
        var lines = parsePdf(URI.create(URL));

        var dayInSwedish = WeekDays.WEEK_DAYS_TO_SWEDISH.get(dayOfWeek);

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
        return Optional.of(new Meal(desc, price));
    }

    private List<String> parsePdf(URI url) {
        try (var document = PDDocument.load(url.toURL().openStream())) {
            if (document.isEncrypted()) {
                throw new UnsupportedOperationException("Can not parse encrypted pdf's");
            }

            var textStripper = new PDFTextStripper();
            var text = textStripper.getText(document);

            return text.lines()
                    .filter(not(String::isEmpty))
                    .map(String::strip)
                    .collect(toList());
        } catch (IOException e) {
            throw new RuntimeException(URL, e);
        }
    }
}