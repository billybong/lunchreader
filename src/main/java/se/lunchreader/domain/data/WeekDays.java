package se.lunchreader.domain.data;

import java.time.DayOfWeek;
import java.util.Collection;
import java.util.Map;

import static java.time.DayOfWeek.*;

public class WeekDays {
    static final public Map<DayOfWeek, String> WEEK_DAYS_TO_SWEDISH = Map.of(
            MONDAY, "Måndag",
            TUESDAY, "Tisdag",
            WEDNESDAY, "Onsdag",
            THURSDAY, "Torsdag",
            FRIDAY, "Fredag",
            SATURDAY, "Lördag",
            SUNDAY, "Söndag"
    );
    static final public Collection<String> SWEDISH_WEEK_DAYS = WEEK_DAYS_TO_SWEDISH.values();
}
