package se.lunchreader.infrastructure.client;

import java.net.URI;
import java.util.List;

public interface IPdfParser {
    List<String> parsePdf(URI url);
}
