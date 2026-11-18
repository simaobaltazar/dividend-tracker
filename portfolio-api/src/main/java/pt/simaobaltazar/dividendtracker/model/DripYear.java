package pt.simaobaltazar.dividendtracker.model;

import java.math.BigDecimal;

public class DripYear {

    private final int year;
    private final BigDecimal grossDividend;
    private final BigDecimal tax;
    private final BigDecimal netDividend;
    private final BigDecimal sharesBought;
    private final BigDecimal shares;
    private final BigDecimal positionValue;

    public DripYear(int year, BigDecimal grossDividend, BigDecimal tax, BigDecimal netDividend,
                    BigDecimal sharesBought, BigDecimal shares, BigDecimal positionValue) {
        this.year = year;
        this.grossDividend = grossDividend;
        this.tax = tax;
        this.netDividend = netDividend;
        this.sharesBought = sharesBought;
        this.shares = shares;
        this.positionValue = positionValue;
    }

    public int getYear() { return year; }

    public BigDecimal getGrossDividend() { return grossDividend; }

    public BigDecimal getTax() { return tax; }

    public BigDecimal getNetDividend() { return netDividend; }

    public BigDecimal getSharesBought() { return sharesBought; }

    public BigDecimal getShares() { return shares; }

    public BigDecimal getPositionValue() { return positionValue; }
}
