package es.develex.application;

import es.develex.domain.QuoteOffer;
import es.develex.domain.QuoteResult;

import java.util.Comparator;
import java.util.List;

public class QuoteCalculator {
    private static final String SEPARATOR = ",";
    private static final int NUM_MONTHS_LOAN = 36;
    private final DataReader dataReader;
    private final OutputPrinter outputPrinter;

    public QuoteCalculator(DataReader dataReader, OutputPrinter outputPrinter) {
        this.dataReader = dataReader;
        this.outputPrinter = outputPrinter;
    }

    public void calculate(String marketFile, Integer loanAmount) {
        List<QuoteOffer> quoteOffers = readOffers(marketFile);
        quoteOffers.sort(Comparator.comparing(QuoteOffer::getRate));

        if (hasEnoughQuotes(quoteOffers, loanAmount)) {
            QuoteResult quoteResult = calculateQuote(quoteOffers, loanAmount);
            outputPrinter.printQuote(loanAmount, quoteResult);
        } else {
            outputPrinter.printMessage("Is not possible to provide a quote at that time.");
        }
    }

    private boolean hasEnoughQuotes(List<QuoteOffer> quoteOffers, Integer loanAmount) {
        return quoteOffers.stream().mapToInt(quote -> quote.getAvailable()).sum() >= loanAmount;
    }

    private QuoteResult calculateQuote(List<QuoteOffer> quoteOffers, Integer loanAmount) {
        double totalRepayment = getTotalRepayment(quoteOffers, loanAmount);

        double rate = (totalRepayment - loanAmount) * 100 / loanAmount;
        double monthlyRepayment = totalRepayment / NUM_MONTHS_LOAN;

        return new QuoteResult(rate, monthlyRepayment, totalRepayment);
    }

    private double getTotalRepayment(List<QuoteOffer> quoteOffers, Integer loanAmount) {
        int borrowed = 0;
        double totalRepayment = 0;

        for (QuoteOffer offer : quoteOffers) {
            double amountToBorrow = loanAmount < borrowed + offer.getAvailable() ? loanAmount - borrowed : offer.getAvailable();

            totalRepayment += amountToBorrow * (1 + offer.getRate());

            if ((borrowed += amountToBorrow) >= loanAmount) {
                break;
            }
        }

        return totalRepayment;
    }

    private List<QuoteOffer> readOffers(String marketFile) {
        return dataReader.readOffers(marketFile, SEPARATOR);
    }
}
