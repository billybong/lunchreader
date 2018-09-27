package se.lunchreader;

import se.lunchreader.infrastructure.Bootstrapper;
import se.lunchreader.infrastructure.Config;
import se.lunchreader.infrastructure.Factories;

public class Main {

    public static void main(String[] args) {
        var config = Config.load();
        var app = Bootstrapper.bootstrap(config, Factories.noStub());
        app.start();
    }
}
