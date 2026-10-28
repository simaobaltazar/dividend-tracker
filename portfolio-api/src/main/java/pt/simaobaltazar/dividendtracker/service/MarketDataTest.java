package pt.simaobaltazar.dividendtracker.service;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MarketDataTest implements ApplicationRunner {

    private final MarketDataClient marketDataClient;

    public MarketDataTest(MarketDataClient marketDataClient) {
        this.marketDataClient = marketDataClient;
    }

    @Override
    public void run(ApplicationArguments args) {
        System.out.println(marketDataClient.getQuote("AAPL"));
    }
}