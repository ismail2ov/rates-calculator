package es.develex.infrastructure;

import es.develex.application.OutputPrinter;
import es.develex.domain.QuoteResult;

import java.math.RoundingMode;
import java.text.NumberFormat;

public class OutputPrinterImpl implements OutputPrinter {

    public void printQuote(Integer loanAmount, QuoteResult quoteResult) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        numberFormat.setGroupingUsed(false);
        numberFormat.setRoundingMode(RoundingMode.HALF_UP);

        numberFormat.setMaximumFractionDigits(0);
        System.out.printf("Requested amount: %s \n", numberFormat.format(loanAmount));
        System.out.printf("Rate: %.1f%% \n", quoteResult.getRate() * 100);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
        System.out.printf("Monthly repayment: %s \n", numberFormat.format(quoteResult.getMonthlyRepayment()));
        System.out.printf("Total repayment: %s \n", numberFormat.format(quoteResult.getTotalRepayment()));
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }
}
