package es.develex.domain;

public class QuoteOffer {
    private String lender;
    private double rate;
    private int available;

    public QuoteOffer(String lender, double rate, int available) {
        this.lender = lender;
        this.rate = rate;
        this.available = available;
    }

    public String getLender() {
        return lender;
    }

    public double getRate() {
        return rate;
    }

    public int getAvailable() {
        return available;
    }
}
