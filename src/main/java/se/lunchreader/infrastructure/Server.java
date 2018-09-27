package se.lunchreader.infrastructure;

import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;
import se.lunchreader.domain.service.RestaurantFinder;

import static io.javalin.apibuilder.ApiBuilder.get;
import static java.time.LocalDate.now;

public class Server {

    private final Config config;
    private final RestaurantFinder restaurantFinder;
    private final Javalin javalin;

    public Server(Config config, RestaurantFinder restaurantFinder) {
        this.config = config;
        this.restaurantFinder = restaurantFinder;
        javalin = Javalin.create().routes(this.routes());
    }

    private EndpointGroup routes() {
        return () -> {
            get("/", ctx -> ctx.result("Hello and welcome to the lunchreader. Call /lunch for todays lunch"));
            get("lunch", ctx -> ctx.json(restaurantFinder.todaysMenus()));
        };
    }

    public void start(){
        javalin.start(config.port);
    }

    public void stop(){
        javalin.stop();
    }
}