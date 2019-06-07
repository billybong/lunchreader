package se.lunchreader.infrastructure;

import se.lunchreader.domain.restaurants.mynchen.Mynchen;
import se.lunchreader.infrastructure.client.PdfParser;

import java.util.Optional;

public interface Stubs {
    default Optional<Mynchen> mynchen() {return Optional.empty();}
    static Stubs noStub() {return new Stubs() {};}

    default Optional<PdfParser> laulauReader() { return Optional.empty();};
}
