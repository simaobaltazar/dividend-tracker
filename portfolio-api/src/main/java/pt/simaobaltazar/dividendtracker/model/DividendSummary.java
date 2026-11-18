package pt.simaobaltazar.dividendtracker.model;

import java.math.BigDecimal;
import java.util.List;

public record DividendSummary(List<DividendForecast> forecasts,
                              BigDecimal totalGrossEur,
                              BigDecimal totalNetEur) {
}
