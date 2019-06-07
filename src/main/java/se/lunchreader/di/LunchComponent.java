package se.lunchreader.di;

import dagger.Component;
import se.lunchreader.infrastructure.Server;

import javax.inject.Singleton;

@Singleton
@Component(modules = {LunchModule.class})
public interface LunchComponent {
    Server server();
}
