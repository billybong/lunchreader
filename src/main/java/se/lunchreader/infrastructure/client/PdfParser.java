package se.lunchreader.infrastructure.client;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.util.List;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;

public class PdfParser implements IPdfParser {

    @Override
    public List<String> parsePdf(URI url) {
        try (var document = PDDocument.load(url.toURL().openStream())) {
            if (document.isEncrypted()) {
                throw new UnsupportedOperationException("Can not parse encrypted pdf's");
            }

            var textStripper = new PDFTextStripper();
            var text = textStripper.getText(document);

            return text.lines()
                    .filter(not(String::isEmpty))
                    .map(String::strip)
                    .collect(toList());
        } catch (IOException e) {
            throw new UncheckedIOException(url.toString(), e);
        }
    }
}
