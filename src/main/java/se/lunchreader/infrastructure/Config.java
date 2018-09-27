package se.lunchreader.infrastructure;

public class Config {
    public int port = 8080;

    private Config() {}

    public static Config load() {
        return new Config();
    }
}
