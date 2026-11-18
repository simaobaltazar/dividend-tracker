package pt.simaobaltazar.dividendtracker.service;

import org.springframework.stereotype.Service;
import pt.simaobaltazar.dividendtracker.model.DividendForecast;
import pt.simaobaltazar.dividendtracker.model.DividendSummary;
import pt.simaobaltazar.dividendtracker.model.Holding;
import pt.simaobaltazar.dividendtracker.model.Quote;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class DividendForecastService {
    private static final BigDecimal TAX_RATE = new BigDecimal("0.28");

    private final PortfolioService portfolioService;
    private final MarketDataClient marketDataClient;

    public DividendForecastService(PortfolioService portfolioService, MarketDataClient marketDataClient) {
        this.portfolioService = portfolioService;
        this.marketDataClient = marketDataClient;
    }

    public DividendSummary summary() {
        List<DividendForecast> forecasts = portfolioService.portfolio().stream()
                .map(this::forecastOf)
                .toList();
        return new DividendSummary(forecasts, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    private DividendForecast forecastOf(Holding holding) {
        Quote quote = marketDataClient.getQuote(holding.assetTicker());

        BigDecimal annualGross = holding.quantity()
                .multiply(quote.annualDividend())
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal grossPerPayment = BigDecimal.ZERO;
        if (quote.paymentsPerYear() > 0) {
            grossPerPayment = annualGross.divide(
                    BigDecimal.valueOf(quote.paymentsPerYear()), 2, RoundingMode.HALF_UP);
        }

        BigDecimal tax = annualGross.multiply(TAX_RATE).setScale(2, RoundingMode.HALF_UP);
        BigDecimal annualNet = annualGross.subtract(tax);

        return new DividendForecast(holding.assetTicker(), quote.currency(), annualGross,
                quote.paymentsPerYear(), grossPerPayment, tax, annualNet);
    }
}
