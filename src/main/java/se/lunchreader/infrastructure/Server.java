package se.lunchreader.infrastructure;

import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;
import se.lunchreader.domain.service.RestaurantFinder;
import se.lunchreader.infrastructure.config.Config;

import javax.inject.Inject;
import javax.inject.Singleton;

import static io.javalin.apibuilder.ApiBuilder.get;

@Singleton
public class Server implements AutoCloseable {
    private final RestaurantFinder restaurantFinder;
    private final Javalin javalin;
    private final int port;

    @Inject
    public Server(Config config, RestaurantFinder restaurantFinder) {
        this.restaurantFinder = restaurantFinder;
        this.port = config.port();
        javalin = Javalin.create().routes(this.routes());
    }

    public void start(){
        javalin.start(port);
    }

    private EndpointGroup routes() {
        return () -> {
            get("/", ctx -> ctx.result("Hello and welcome to the lunchreader. Call /lunch for todays lunch"));
            get("lunch", ctx -> ctx.json(restaurantFinder.todaysMenus()));
        };
    }

    @Override
    public void close() {
        javalin.stop();
    }
}