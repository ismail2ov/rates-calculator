package es.develex;

import java.nio.file.Files;
import java.nio.file.Paths;

public class InputParameters {
    private static final int MIN_LOAN_AMOUNT = 1000;
    private static final int MAX_LOAN_AMOUNT = 15000;

    private String marketFile;
    private Integer loanAmount;

    public InputParameters(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("The application is expecting two parameters: [market_file] [loan_amount]");
        }

        marketFile = getValidatedMarketFile(args[0]);
        loanAmount = getValidatedLoanAmount(args[1]);
    }

    public String getMarketFile() {
        return marketFile;
    }

    public Integer getLoanAmount() {
        return loanAmount;
    }

    private String getValidatedMarketFile(String marketFile) {
        if (!Files.exists(Paths.get(marketFile))) {
            throw new IllegalArgumentException("The market file not found!");
        }

        return marketFile;
    }

    private Integer getValidatedLoanAmount(String amount) {
        Integer loanAmount = Integer.parseInt(amount);

        if (loanAmount < MIN_LOAN_AMOUNT || loanAmount > MAX_LOAN_AMOUNT) {
            throw new IllegalArgumentException("Only loan amounts between " + MIN_LOAN_AMOUNT + " and " + MAX_LOAN_AMOUNT + " are allowed");
        }

        if (loanAmount % 100 != 0) {
            throw new IllegalArgumentException("The loan amount must be divisible to 100");
        }

        return loanAmount;
    }
}
