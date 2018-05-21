package es.develex;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class RatesCalculatorShould {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    public void when_requested_amount_is_1000_then_rate_is_7() {
        String[] args = new String[]{"market.csv", "1000"};
        Main.main(args);
        assertThat(outContent.toString(), containsString("Rate: 7,0%"));
    }

    @Test
    public void test_when_requested_amount_is_1500() {
        String[] args = new String[]{"market.csv", "1500"};
        Main.main(args);
        String expected = "Requested amount: £1500 \nRate: 7,1% \nMonthly repayment: £46,26 \nTotal repayment: £1665,39 \n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    public void test_when_requested_amount_is_15000() {
        String[] args = new String[]{"market.csv", "15000"};
        Main.main(args);
        assertThat(outContent.toString(), containsString("Is not possible to provide a quote at that time"));
    }

    @After
    public void tearDown() {
        System.setOut(System.out);
        System.setErr(System.err);
    }
}