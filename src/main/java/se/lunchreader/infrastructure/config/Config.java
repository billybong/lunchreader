package se.lunchreader.infrastructure.config;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URI;

@Singleton
public class Config {

    @Inject
    public Config() {
    }

    public int port() {return 7090;}

    public URI lauLauUri(){
        return URI.create("http://bogus.com");
    }
}
