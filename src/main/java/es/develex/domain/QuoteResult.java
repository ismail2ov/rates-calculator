package es.develex.domain;

public class QuoteResult {
    private double rate;
    private double monthlyRepayment;
    private double totalRepayment;

    public QuoteResult(double rate, double monthlyRepayment, double totalRepayment) {
        this.rate = rate;
        this.monthlyRepayment = monthlyRepayment;
        this.totalRepayment = totalRepayment;
    }

    public double getRate() {
        return rate;
    }

    public double getMonthlyRepayment() {
        return monthlyRepayment;
    }

    public double getTotalRepayment() {
        return totalRepayment;
    }
}
