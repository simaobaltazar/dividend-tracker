package pt.simaobaltazar.dividendtracker.service;

import org.springframework.stereotype.Service;
import pt.simaobaltazar.dividendtracker.model.DripYear;
import pt.simaobaltazar.dividendtracker.model.Holding;
import pt.simaobaltazar.dividendtracker.model.Quote;
import pt.simaobaltazar.dividendtracker.service.exceptions.InvalidRequestException;
import pt.simaobaltazar.dividendtracker.service.exceptions.ResourceNotFoundException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class DripSimulatorService {
    private final PortfolioService portfolioService;
    private final MarketDataClient marketDataClient;

    public DripSimulatorService(PortfolioService portfolioService, MarketDataClient marketDataClient) {
        this.portfolioService = portfolioService;
        this.marketDataClient = marketDataClient;
    }

    public List<DripYear> simulate(String ticker, int years) {
        if (years < 1 || years > 50) {
            throw new InvalidRequestException("Years must be between 1 and 50");
        }

        Holding holding = portfolioService.portfolio().stream()
                .filter(h -> h.getAssetTicker().equals(ticker))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(ticker));

        Quote quote = marketDataClient.getQuote(ticker);
        if (quote.getPaymentsPerYear() == 0) {
            throw new InvalidRequestException(ticker + " does not pay dividends: an accumulating fund already "
                    + "reinvests them, and the growth is in the price");
        }

        List<DripYear> result = new ArrayList<>();
        BigDecimal shares = holding.getQuantity();

        for (int year = 1; year <= years; year++) {
            BigDecimal gross = shares.multiply(quote.getAnnualDividend()).setScale(2, RoundingMode.HALF_UP);
            BigDecimal tax = gross.multiply(DividendForecastService.TAX_RATE).setScale(2, RoundingMode.HALF_UP);
            BigDecimal net = gross.subtract(tax);
            BigDecimal sharesBought = net.divide(quote.getPrice(), 4, RoundingMode.HALF_UP);

            shares = shares.add(sharesBought);
            BigDecimal positionValue = shares.multiply(quote.getPrice()).setScale(2, RoundingMode.HALF_UP);

            result.add(new DripYear(year, gross, tax, net, sharesBought, shares, positionValue));
        }
        return result;
    }
}
