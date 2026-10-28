package pt.simaobaltazar.dividendtracker.model;

import java.math.BigDecimal;

public record Quote(String ticker,
                    BigDecimal price,
                    String currency,
                    BigDecimal annualDividend,
                    int paymentsPerYear) {
}
