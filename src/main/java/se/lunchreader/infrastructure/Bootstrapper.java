package se.lunchreader.infrastructure;

import se.lunchreader.domain.restaurants.Mynchen;
import se.lunchreader.domain.service.RestaurantFinder;

import java.util.List;

public class Bootstrapper {
    public static Server bootstrap(Config config, Factories stubs) {
        var restaurantFinder = new RestaurantFinder(List.of(new Mynchen()));

        return new Server(config, restaurantFinder);
    }
}
