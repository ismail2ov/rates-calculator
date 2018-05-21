package es.develex.infrastructure;

import es.develex.domain.QuoteResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.RoundingMode;
import java.text.NumberFormat;

import static org.junit.Assert.assertEquals;

public class OutputPrinterImplTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    public void printQuote() {
        Integer loanAmount = 1000;
        QuoteResult quoteResult = new QuoteResult(0.07, 30.78, 1108.10);

        OutputPrinterImpl outputPrinter = new OutputPrinterImpl();
        outputPrinter.printQuote(loanAmount, quoteResult);

        String expected = getExpectedString(loanAmount, quoteResult);
        assertEquals(expected, outContent.toString());
    }

    private String getExpectedString(Integer loanAmount, QuoteResult quoteResult) {
        String expected = "";
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        numberFormat.setGroupingUsed(false);
        numberFormat.setRoundingMode(RoundingMode.HALF_UP);

        numberFormat.setMaximumFractionDigits(0);
        expected += "Requested amount: " + numberFormat.format(loanAmount) + " \n";
        expected += String.format("Rate: %.1f%% \n", quoteResult.getRate() * 100);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
        expected += "Monthly repayment: " + numberFormat.format(quoteResult.getMonthlyRepayment()) + " \n";
        expected += "Total repayment: " + numberFormat.format(quoteResult.getTotalRepayment()) + " \n";

        return expected;
    }

    @After
    public void tearDown() {
        System.setOut(System.out);
        System.setErr(System.err);
    }
}