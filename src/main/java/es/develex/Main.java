package es.develex;

import es.develex.application.QuoteCalculator;
import es.develex.infrastructure.DataReaderImpl;
import es.develex.infrastructure.InputParameters;
import es.develex.infrastructure.OutputPrinterImpl;

public class Main {

    public static void main(String[] args) {
        try {
            InputParameters inputParameters = new InputParameters(args);
            QuoteCalculator quoteCalculator = new QuoteCalculator(new DataReaderImpl(), new OutputPrinterImpl());
            quoteCalculator.calculate(inputParameters.getMarketFile(), inputParameters.getLoanAmount());
        } catch (Exception e) {
            OutputPrinterImpl.printErrorMessage(e.getMessage());
        }
    }
}
