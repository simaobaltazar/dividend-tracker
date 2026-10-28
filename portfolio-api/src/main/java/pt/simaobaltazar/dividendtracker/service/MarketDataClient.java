package pt.simaobaltazar.dividendtracker.service;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.springframework.stereotype.Service;
import pt.simaobaltazar.dividendtracker.grpc.MarketDataServiceGrpc;
import pt.simaobaltazar.dividendtracker.grpc.QuoteRequest;
import pt.simaobaltazar.dividendtracker.grpc.QuoteResponse;
import pt.simaobaltazar.dividendtracker.model.Quote;
import pt.simaobaltazar.dividendtracker.service.exceptions.ResourceNotFoundException;

import java.math.BigDecimal;

@Service
public class MarketDataClient {

    private final MarketDataServiceGrpc.MarketDataServiceBlockingStub stub;

    public MarketDataClient(MarketDataServiceGrpc.MarketDataServiceBlockingStub stub){
        this.stub = stub;
    }

    public Quote getQuote(String ticker) {
        QuoteRequest request = QuoteRequest.newBuilder()
                .setTicker(ticker)
                .build();
        try {
            QuoteResponse response = stub.getQuote(request);
            return new Quote(
                    response.getTicker(),
                    new BigDecimal(response.getPrice()),
                    response.getCurrency(),
                    new BigDecimal(response.getAnnualDividend()),
                    response.getPaymentsPerYear());
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                throw new ResourceNotFoundException(ticker);
            }
            throw e;
        }
    }
}
