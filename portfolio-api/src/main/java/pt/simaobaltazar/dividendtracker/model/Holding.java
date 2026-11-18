package pt.simaobaltazar.dividendtracker.model;

import java.math.BigDecimal;

public class Holding {

    private final String assetTicker;
    private final BigDecimal quantity;
    private final BigDecimal averagePrice;
    private final BigDecimal realizedProfit;
    private final BigDecimal weight;

    public Holding(String assetTicker, BigDecimal quantity, BigDecimal averagePrice,
                   BigDecimal realizedProfit, BigDecimal weight) {
        this.assetTicker = assetTicker;
        this.quantity = quantity;
        this.averagePrice = averagePrice;
        this.realizedProfit = realizedProfit;
        this.weight = weight;
    }

    public String getAssetTicker() { return assetTicker; }

    public BigDecimal getQuantity() { return quantity; }

    public BigDecimal getAveragePrice() { return averagePrice; }

    public BigDecimal getRealizedProfit() { return realizedProfit; }

    public BigDecimal getWeight() { return weight; }
}
