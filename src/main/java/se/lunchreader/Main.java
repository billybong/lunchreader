package se.lunchreader;

import se.lunchreader.di.DaggerLunchComponent;

public class Main {

    public static void main(String[] args) {
        var lunchComponent = DaggerLunchComponent.create();
        final var server = lunchComponent.server();
        server.start();
    }
}
