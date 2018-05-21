package es.develex;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.RoundingMode;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class RatesCalculatorShould {
    private static final String TEST_CSV_FILE_PATH = System.getProperty("user.dir") + "\\src\\test\\java\\resources\\market.csv";
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    public void when_requested_amount_is_1000_then_rate_is_7() {
        String[] args = new String[]{TEST_CSV_FILE_PATH, "1000"};
        Main.main(args);

        char decimalSeparator = DecimalFormatSymbols.getInstance().getDecimalSeparator();
        assertThat(outContent.toString(), containsString("Rate: 7" + decimalSeparator + "0%"));
    }

    @Test
    public void test_when_requested_amount_is_1500() {
        int loanAmount = 1500;
        String[] args = new String[]{TEST_CSV_FILE_PATH, String.valueOf(loanAmount)};
        Main.main(args);
        String expected = getExpectedString(loanAmount, 0.071, 46.26, 1665.39);
        assertEquals(expected, outContent.toString());
    }

    @Test
    public void test_when_requested_amount_is_15000() {
        String[] args = new String[]{TEST_CSV_FILE_PATH, "15000"};
        Main.main(args);
        assertThat(outContent.toString(), containsString("Is not possible to provide a quote at that time"));
    }

    private String getExpectedString(Integer loanAmount, double rate, double monthlyRepayment, double totalRepayment) {
        String expected = "";
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        numberFormat.setGroupingUsed(false);
        numberFormat.setRoundingMode(RoundingMode.HALF_UP);

        numberFormat.setMaximumFractionDigits(0);
        expected += "Requested amount: " + numberFormat.format(loanAmount) + " \n";
        expected += String.format("Rate: %.1f%% \n", rate * 100);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
        expected += "Monthly repayment: " + numberFormat.format(monthlyRepayment) + " \n";
        expected += "Total repayment: " + numberFormat.format(totalRepayment) + " \n";

        return expected;
    }

    @After
    public void tearDown() {
        System.setOut(System.out);
        System.setErr(System.err);
    }
}