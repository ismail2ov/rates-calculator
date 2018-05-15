package es.develex.infrastructure;

import es.develex.application.DataReader;
import es.develex.domain.QuoteOffer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataReaderImpl implements DataReader {
    public List<QuoteOffer> readOffers(String marketFile, String separator) {

        List<QuoteOffer> quoteOffers = new ArrayList<>();
        boolean header = true;
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(marketFile));

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (header) {
                    header = false;
                    continue;
                }

                String[] fields = line.split(separator);
                if (fields.length == 3) {
                    quoteOffers.add(new QuoteOffer(fields[0], Double.parseDouble(fields[1]), Integer.parseInt(fields[2])));
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }


        return quoteOffers;
    }
}
