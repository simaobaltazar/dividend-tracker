package pt.simaobaltazar.dividendtracker.model;

import java.math.BigDecimal;

public record DividendForecast(String assetTicker,
                               String currency,
                               BigDecimal annualGross,
                               int paymentsPerYear,
                               BigDecimal grossPerPayment,
                               BigDecimal tax,
                               BigDecimal annualNet) {
}
