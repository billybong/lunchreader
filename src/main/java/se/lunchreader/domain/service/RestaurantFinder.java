package se.lunchreader.domain.service;

import com.google.auto.value.AutoValue;
import se.lunchreader.domain.data.Meal;
import se.lunchreader.domain.data.Restaurant;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

import static java.time.LocalDate.now;
import static java.util.stream.Collectors.toList;

@Singleton
public class RestaurantFinder {

    private final List<Restaurant> restaurants;

    @Inject
    public RestaurantFinder(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public List<RestaurantsResponse> todaysMenus() {

        return restaurants.stream()
                .map(restaurant -> RestaurantsResponse.create(restaurant.name(), restaurant.onMenu(now().getDayOfWeek())))
                .collect(toList());
    }
}
