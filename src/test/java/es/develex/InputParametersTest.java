package es.develex;

import org.junit.Test;

public class InputParametersTest {

    @Test(expected = IllegalArgumentException.class)
    public void when_no_arguments_then_throw_exception() {
        String[] args = new String[]{};
        new InputParameters(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void when_the_loan_amount_is_lower_than_range_then_throw_exception() {
        String[] args = new String[]{"market.csv", "900"};
        new InputParameters(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void when_the_loan_amount_is_greater_than_range_then_throw_exception() {
        String[] args = new String[]{"market.csv", "16000"};
        new InputParameters(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void when_the_loan_amount_is_not_divibles_to_100_then_throw_exception() {
        String[] args = new String[]{"market.csv", "1010"};
        new InputParameters(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void when_market_file_does_not_exists_then_throw_exception() {
        String[] args = new String[]{"marketFile.csv", "1500"};
        new InputParameters(args);
    }
}