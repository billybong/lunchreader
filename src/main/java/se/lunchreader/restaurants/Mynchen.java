package se.lunchreader.restaurants;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import se.lunchreader.WeekDays;
import se.lunchreader.domain.Meal;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.time.LocalDate.now;
import static java.util.stream.Collectors.toList;

public class Mynchen {
    private static final String URL = "https://static1.squarespace.com/static/578916fc725e25c4aa70ea79/t/5ba0a67c575d1ff43f1db1d7/1537255036662/veckanslunch.pdf";
    private static final Pattern REGEX = Pattern.compile("(\\D*) (\\d*):-");

    public List<Meal> onMenu(DayOfWeek dayOfWeek) throws IOException {
        var lines = parsePdf(new URL(URL));
        var todaysMenu = findTodaysMenu(lines, dayOfWeek);
        var allwaysOnMenu = findAllwaysOnMenu(lines);

        return Stream.concat(todaysMenu, allwaysOnMenu)
                .flatMap(line -> parseMeal(line).stream())
                .collect(toList());
    }

    private Optional<Meal> parseMeal(String line) {
        var matcher = REGEX.matcher(line);
        if (!matcher.matches() || matcher.groupCount() != 2){
            return Optional.empty();
        }

        var desc = matcher.group(1);
        var price = Double.parseDouble(matcher.group(2));
        return Optional.of(new Meal(desc, price));
    }

    private Stream<String> findAllwaysOnMenu(List<String> lines) {
        return lines.stream()
                .dropWhile(line -> !line.equals("Hela veckan"))
                .dropWhile(line -> line.equals("Hela veckan"))
                .takeWhile(line -> !line.equals("Sushi"));
    }

    private Stream<String> findTodaysMenu(List<String> lines, DayOfWeek dayOfWeek) {
        String todayInSwedish = WeekDays.WEEK_DAYS_TO_SWEDISH.get(dayOfWeek);
        return lines.stream()
                .dropWhile(line -> !line.equals(todayInSwedish))
                .dropWhile(line -> line.equals(todayInSwedish))
                .takeWhile(line -> !WeekDays.SWEDISH_WEEK_DAYS.contains(line));
    }

    private List<String> parsePdf(URL url) throws IOException {
        try (PDDocument document = PDDocument.load(url.openStream())) {
            if (document.isEncrypted()) {
                throw new UnsupportedOperationException("Can not parse encrypted pdf's");
            }

            var textStripper = new PDFTextStripper();
            var text = textStripper.getText(document);

            return Arrays.stream(text.split("\\r?\\n"))
                    .filter(this::nonEmptyLine)
                    .map(this::strip)
                    .collect(toList());
        }
    }

    private boolean nonEmptyLine(String it) {
        return !it.isEmpty();
    }

    private String strip(String it) {
        return it.stripLeading().stripTrailing();
    }
}