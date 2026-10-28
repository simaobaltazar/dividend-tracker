package pt.simaobaltazar.dividendtracker.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import pt.simaobaltazar.dividendtracker.model.Quote;
import pt.simaobaltazar.dividendtracker.service.MarketDataClient;

@Controller
public class QuoteController {

    private final MarketDataClient marketDataClient;

    public QuoteController(MarketDataClient marketDataClient) {
        this.marketDataClient = marketDataClient;
    }

    @QueryMapping
    public Quote quote(@Argument String ticker) {
        return marketDataClient.getQuote(ticker);
    }
}