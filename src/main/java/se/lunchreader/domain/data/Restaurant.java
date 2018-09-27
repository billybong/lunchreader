package se.lunchreader.domain.data;

import java.time.DayOfWeek;
import java.util.List;

public interface Restaurant {
    List<Meal> onMenu(DayOfWeek dayOfWeek);
    String name();
}
