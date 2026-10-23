package pt.simaobaltazar.dividendtracker.service;

import org.springframework.stereotype.Service;
import pt.simaobaltazar.dividendtracker.model.Holding;
import pt.simaobaltazar.dividendtracker.model.Transaction;
import pt.simaobaltazar.dividendtracker.model.TransactionType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PortfolioService {

    private final TransactionService transactionService;

    public PortfolioService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public List<Holding> portfolio() {
        Map<String, List<Transaction>> byTicker = transactionService.findAll().stream()
                .collect(Collectors.groupingBy(t -> t.assetTicker()));

        return byTicker.entrySet().stream()
                .map(entry -> calculateHolding(entry.getKey(), entry.getValue()))
                .filter(holding -> holding.quantity().compareTo(BigDecimal.ZERO) > 0)
                .toList();
    }

    private Holding calculateHolding(String ticker, List<Transaction> transactions) {

        BigDecimal quantity = BigDecimal.ZERO;
        BigDecimal totalCost = BigDecimal.ZERO;
        BigDecimal realizedProfit = BigDecimal.ZERO;
        BigDecimal finalAveragePrice = BigDecimal.ZERO;

        List<Transaction> sorted = transactions.stream()
                .sorted(Comparator.comparing(t -> t.date()))
                .toList();

        for (Transaction t : sorted) {

            if (t.type() == TransactionType.BUY) {
                quantity = quantity.add(t.quantity());
                totalCost = totalCost.add(t.quantity().multiply(t.price()));
            } else {
                BigDecimal averagePrice = totalCost.divide(quantity, 4, RoundingMode.HALF_UP);
                BigDecimal profit = t.price().subtract(averagePrice).multiply(t.quantity());
                realizedProfit = realizedProfit.add(profit);
                quantity = quantity.subtract(t.quantity());
                totalCost = totalCost.subtract(t.quantity().multiply(averagePrice));

            }
        }

        if (quantity.compareTo(BigDecimal.ZERO) > 0) {
            finalAveragePrice = totalCost.divide(quantity, 4, RoundingMode.HALF_UP);
        }

        return new Holding(ticker, quantity, finalAveragePrice, realizedProfit);
    }
}