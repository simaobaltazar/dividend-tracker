package pt.simaobaltazar.dividendtracker.service;

import org.springframework.stereotype.Service;
import pt.simaobaltazar.dividendtracker.grpc.MarketDataServiceGrpc;
import pt.simaobaltazar.dividendtracker.grpc.QuoteRequest;
import pt.simaobaltazar.dividendtracker.grpc.QuoteResponse;

@Service
public class MarketDataClient {

    private final MarketDataServiceGrpc.MarketDataServiceBlockingStub stub;

    public MarketDataClient(MarketDataServiceGrpc.MarketDataServiceBlockingStub stub){
        this.stub = stub;
    }

    public QuoteResponse getQuote(String ticker){
        QuoteRequest request = QuoteRequest.newBuilder()
                .setTicker(ticker)
                .build();
        return stub.getQuote(request);
    }
}
