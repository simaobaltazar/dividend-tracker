package pt.simaobaltazar.dividendtracker.model;

import java.math.BigDecimal;

public class DividendForecast {

    private final String assetTicker;
    private final String currency;
    private final BigDecimal annualGross;
    private final int paymentsPerYear;
    private final BigDecimal grossPerPayment;
    private final BigDecimal tax;
    private final BigDecimal annualNet;

    public DividendForecast(String assetTicker, String currency, BigDecimal annualGross,
                            int paymentsPerYear, BigDecimal grossPerPayment, BigDecimal tax,
                            BigDecimal annualNet) {
        this.assetTicker = assetTicker;
        this.currency = currency;
        this.annualGross = annualGross;
        this.paymentsPerYear = paymentsPerYear;
        this.grossPerPayment = grossPerPayment;
        this.tax = tax;
        this.annualNet = annualNet;
    }

    public String getAssetTicker() { return assetTicker; }

    public String getCurrency() { return currency; }

    public BigDecimal getAnnualGross() { return annualGross; }

    public int getPaymentsPerYear() { return paymentsPerYear; }

    public BigDecimal getGrossPerPayment() { return grossPerPayment; }

    public BigDecimal getTax() { return tax; }

    public BigDecimal getAnnualNet() { return annualNet; }
}
