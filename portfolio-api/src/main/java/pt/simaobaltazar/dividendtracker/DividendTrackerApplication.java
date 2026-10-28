package pt.simaobaltazar.dividendtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.grpc.client.ImportGrpcClients;
import pt.simaobaltazar.dividendtracker.grpc.MarketDataServiceGrpc;

@SpringBootApplication
@ImportGrpcClients(target = "market-data", types = MarketDataServiceGrpc.MarketDataServiceBlockingStub.class)
public class DividendTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DividendTrackerApplication.class, args);
    }

}
