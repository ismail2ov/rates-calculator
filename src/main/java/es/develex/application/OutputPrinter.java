package es.develex.application;

import es.develex.domain.QuoteResult;

public interface OutputPrinter {
    void printQuote(Integer loanAmount, QuoteResult quoteResult);
}
