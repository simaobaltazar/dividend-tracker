package pt.simaobaltazar.dividendtracker.model;

import java.math.BigDecimal;
import java.util.List;

public class DividendSummary {

    private final List<DividendForecast> forecasts;
    private final BigDecimal totalGrossEur;
    private final BigDecimal totalNetEur;

    public DividendSummary(List<DividendForecast> forecasts, BigDecimal totalGrossEur, BigDecimal totalNetEur) {
        this.forecasts = forecasts;
        this.totalGrossEur = totalGrossEur;
        this.totalNetEur = totalNetEur;
    }

    public List<DividendForecast> getForecasts() { return forecasts; }

    public BigDecimal getTotalGrossEur() { return totalGrossEur; }

    public BigDecimal getTotalNetEur() { return totalNetEur; }
}
