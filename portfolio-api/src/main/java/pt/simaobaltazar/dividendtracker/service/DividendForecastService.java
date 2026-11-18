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
    public static final BigDecimal TAX_RATE = new BigDecimal("0.28");

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

        BigDecimal totalGrossEur = forecasts.stream()
                .map(f -> toEur(f.annualGross(), f.currency()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalNetEur = forecasts.stream()
                .map(f -> toEur(f.annualNet(), f.currency()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new DividendSummary(forecasts, totalGrossEur, totalNetEur);
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

    private BigDecimal toEur(BigDecimal amount, String currency) {
        if (currency.equals("EUR")) {
            return amount;
        }
        BigDecimal rate = marketDataClient.getQuote("EUR" + currency + "=X").price();
        return amount.divide(rate, 2, RoundingMode.HALF_UP);
    }
}
