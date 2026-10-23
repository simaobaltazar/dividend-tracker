package pt.simaobaltazar.dividendtracker.model;

import java.math.BigDecimal;

public record Holding( String assetTicker,
                      BigDecimal quantity,
                      BigDecimal averagePrice,
                      BigDecimal realizedProfit) {
}
