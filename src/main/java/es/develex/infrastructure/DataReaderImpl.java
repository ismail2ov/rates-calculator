package es.develex.infrastructure;

import es.develex.application.DataReader;
import es.develex.domain.QuoteOffer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DataReaderImpl implements DataReader {
    private String separator;

    public DataReaderImpl(String separator) {
        this.separator = separator;
    }

    public List<QuoteOffer> readOffers(String marketFile) {

        List<QuoteOffer> quoteOffers = new ArrayList<>();

        try (InputStream inputStream = new FileInputStream(new File(marketFile))) {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            quoteOffers = br.lines().skip(1).map(mapToQuoteOffer).collect(Collectors.toList());
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return quoteOffers;
    }

    private Function<String, QuoteOffer> mapToQuoteOffer = (line) -> {
        String[] fields = line.split(this.separator);

        return new QuoteOffer(fields[0], Double.parseDouble(fields[1]), Integer.parseInt(fields[2]));
    };
}
