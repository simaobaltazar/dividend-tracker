package pt.simaobaltazar.dividendtracker.service;

import org.springframework.stereotype.Service;
import pt.simaobaltazar.dividendtracker.model.Holding;
import pt.simaobaltazar.dividendtracker.model.Transaction;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PortfolioService {

    private final TransactionService transactionService;

    public PortfolioService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public List<Holding> portfolio(){
        Map<String,List<Transaction>> byTicker = transactionService.findAll().stream()
                .collect(Collectors.groupingBy(t -> t.assetTicker()));

        return List.of();
    }
}