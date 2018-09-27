package se.lunchreader.domain.service;

import se.lunchreader.domain.data.Meal;
import se.lunchreader.domain.data.Restaurant;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.time.LocalDate.now;

public class RestaurantFinder {

    private final List<Restaurant> restaurants;

    public RestaurantFinder(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public Map<Restaurant, List<Meal>> todaysMenus() {
        return restaurants.stream()
                .collect(Collectors.toMap(
                    Function.identity(),
                    restaurant -> restaurant.onMenu(now().getDayOfWeek())
                ));
    }
}
