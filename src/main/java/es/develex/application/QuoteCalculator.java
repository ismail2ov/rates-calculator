package es.develex.application;

import es.develex.domain.NotFoundQuoteException;
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

    public void calculate(String marketFile, Integer loanAmount)  throws NotFoundQuoteException {
        List<QuoteOffer> quoteOffers = readOffers(marketFile);
        quoteOffers.sort(Comparator.comparing(QuoteOffer::getRate));

        QuoteResult quoteResult = calculateQuote(quoteOffers, loanAmount);
        outputPrinter.printQuote(loanAmount, quoteResult);
    }

    private QuoteResult calculateQuote(List<QuoteOffer> quoteOffers, Integer loanAmount) throws NotFoundQuoteException {
        double totalRepayment = getTotalRepayment(quoteOffers, loanAmount);

        double rate = (totalRepayment - loanAmount) * 100 / loanAmount;
        double monthlyRepayment = totalRepayment / NUM_MONTHS_LOAN;

        return new QuoteResult(rate, monthlyRepayment, totalRepayment);
    }

    private double getTotalRepayment(List<QuoteOffer> quoteOffers, Integer loanAmount) throws NotFoundQuoteException {
        int borrowed = 0;
        double totalRepayment = 0;

        for (QuoteOffer offer : quoteOffers) {
            double amountToBorrow = loanAmount < borrowed + offer.getAvailable() ? loanAmount - borrowed : offer.getAvailable();

            totalRepayment += amountToBorrow * (1 + offer.getRate());

            if ((borrowed += amountToBorrow) >= loanAmount) {
                break;
            }
        }

        if (borrowed < loanAmount) {
            throw new NotFoundQuoteException("Is not possible to provide a quote at that time.");
        }


        return totalRepayment;
    }

    private List<QuoteOffer> readOffers(String marketFile) {
        return dataReader.readOffers(marketFile, SEPARATOR);
    }
}
