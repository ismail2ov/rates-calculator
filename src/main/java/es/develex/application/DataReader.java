package es.develex.application;

import es.develex.domain.QuoteOffer;

import java.util.List;

public interface DataReader {
    List<QuoteOffer> readOffers(String marketFile);
}
