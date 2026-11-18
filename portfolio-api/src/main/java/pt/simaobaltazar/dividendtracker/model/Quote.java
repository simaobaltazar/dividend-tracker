package pt.simaobaltazar.dividendtracker.model;

import java.math.BigDecimal;

public class Quote {

    private final String ticker;
    private final BigDecimal price;
    private final String currency;
    private final BigDecimal annualDividend;
    private final int paymentsPerYear;

    public Quote(String ticker, BigDecimal price, String currency, BigDecimal annualDividend,
                 int paymentsPerYear) {
        this.ticker = ticker;
        this.price = price;
        this.currency = currency;
        this.annualDividend = annualDividend;
        this.paymentsPerYear = paymentsPerYear;
    }

    public String getTicker() { return ticker; }

    public BigDecimal getPrice() { return price; }

    public String getCurrency() { return currency; }

    public BigDecimal getAnnualDividend() { return annualDividend; }

    public int getPaymentsPerYear() { return paymentsPerYear; }
}
