package se.lunchreader.domain.service;

import se.lunchreader.domain.data.Meal;
import se.lunchreader.domain.data.Restaurant;

import java.util.List;

import static java.time.LocalDate.now;
import static java.util.stream.Collectors.toList;

public class RestaurantFinder {

    private final List<Restaurant> restaurants;

    public RestaurantFinder(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public List<RestaurantsResponse> todaysMenus() {
        return restaurants.stream()
                .map(restaurant -> new RestaurantsResponse(restaurant.name(), restaurant.onMenu(now().getDayOfWeek())))
                .collect(toList());
    }

    public static class RestaurantsResponse {
        public final String restaurant;
        public final List<Meal> meals;

        public RestaurantsResponse(String restaurant, List<Meal> meals) {
            this.restaurant = restaurant;
            this.meals = meals;
        }
    }
}
