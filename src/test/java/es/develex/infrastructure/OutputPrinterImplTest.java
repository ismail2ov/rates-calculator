package es.develex.infrastructure;

import es.develex.Main;
import es.develex.domain.QuoteResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

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
        QuoteResult quoteResult = new QuoteResult(7, 29.72, 1070.04);

        OutputPrinterImpl outputPrinter = new OutputPrinterImpl();
        outputPrinter.printQuote(loanAmount, quoteResult);

        String expected = "Requested amount: £1000 \nRate: 7,0% \nMonthly repayment: £29,72 \nTotal repayment: £1070,04 \n";
        assertEquals(expected, outContent.toString());
    }

    @After
    public void tearDown() {
        System.setOut(System.out);
        System.setErr(System.err);
    }
}