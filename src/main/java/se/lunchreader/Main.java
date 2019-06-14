package se.lunchreader;

import se.lunchreader.di.DaggerLunchComponent;

public class Main {

    public static void main(String[] args) {
        var lunchComponent = DaggerLunchComponent.builder().build();
        final var server = lunchComponent.server();
        server.start();
    }
}
