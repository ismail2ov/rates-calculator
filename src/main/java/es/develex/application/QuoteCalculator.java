package es.develex.application;

import es.develex.domain.QuoteOffer;
import es.develex.domain.QuoteResult;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class QuoteCalculator {
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
        return quoteOffers.stream().mapToInt(QuoteOffer::getAvailable).sum() >= loanAmount;
    }

    private QuoteResult calculateQuote(List<QuoteOffer> quoteOffers, Integer loanAmount) {
        List<QuoteOffer> selectedQuoteOffers = getSelectedQuoteOffers(quoteOffers, loanAmount);
        double rate = calculateRate(selectedQuoteOffers, loanAmount);
        double interestRate = Math.pow((1 + rate), (double) 1 / 12) - 1;
        double monthlyRepayment = (loanAmount * interestRate) / (1 - Math.pow(1 + interestRate, -NUM_MONTHS_LOAN));
        double totalRepayment = monthlyRepayment * NUM_MONTHS_LOAN;

        return new QuoteResult(rate, monthlyRepayment, totalRepayment);
    }

    private List<QuoteOffer> getSelectedQuoteOffers(List<QuoteOffer> quoteOffers, Integer loanAmount) {
        List<QuoteOffer> selectedQuoteOffers = new ArrayList<>();
        int borrowed = 0;

        for (QuoteOffer offer : quoteOffers) {
            int amountToBorrow = loanAmount < borrowed + offer.getAvailable() ? loanAmount - borrowed : offer.getAvailable();
            selectedQuoteOffers.add(new QuoteOffer(offer.getLender(), offer.getRate(), amountToBorrow));

            if ((borrowed += offer.getAvailable()) >= loanAmount) {
                break;
            }
        }

        return selectedQuoteOffers;
    }

    private double calculateRate(List<QuoteOffer> selectedQuoteOffers, Integer loanAmount) {
        double simpleTotalRepayment = selectedQuoteOffers.stream().mapToDouble(offer -> offer.getAvailable() * (1 + offer.getRate())).sum();

        return (simpleTotalRepayment - loanAmount) / loanAmount;
    }

    private List<QuoteOffer> readOffers(String marketFile) {
        return dataReader.readOffers(marketFile);
    }
}
