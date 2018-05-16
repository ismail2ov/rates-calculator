package es.develex.infrastructure;

import es.develex.application.OutputPrinter;
import es.develex.domain.QuoteResult;

public class OutputPrinterImpl implements OutputPrinter {
    public void printQuote(Integer loanAmount, QuoteResult quoteResult) {
        System.out.printf("Requested amount: £%d \n", loanAmount);
        System.out.printf("Rate: %.1f%% \n", quoteResult.getRate());
        System.out.printf("Monthly repayment: £%.2f \n", quoteResult.getMonthlyRepayment());
        System.out.printf("Total repayment: £%.2f \n", quoteResult.getTotalRepayment());
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }
}
