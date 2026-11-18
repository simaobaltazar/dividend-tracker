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
                .collect(Collectors.groupingBy(t -> t.getAssetTicker()));

        List<Holding> holdings = byTicker.entrySet().stream()
                .map(entry -> calculateHolding(entry.getKey(), entry.getValue()))
                .filter(holding -> holding.getQuantity().compareTo(BigDecimal.ZERO) > 0)
                .toList();

        BigDecimal total = holdings.stream()
                .map(this::costOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return holdings.stream()
                .map(h -> withWeight(h, total))
                .toList();
    }

    private Holding calculateHolding(String ticker, List<Transaction> transactions) {

        BigDecimal quantity = BigDecimal.ZERO;
        BigDecimal totalCost = BigDecimal.ZERO;
        BigDecimal realizedProfit = BigDecimal.ZERO;
        BigDecimal finalAveragePrice = BigDecimal.ZERO;

        List<Transaction> sorted = transactions.stream()
                .sorted(Comparator.comparing(t -> t.getDate()))
                .toList();

        for (Transaction t : sorted) {

            if (t.getType() == TransactionType.BUY) {
                quantity = quantity.add(t.getQuantity());
                totalCost = totalCost.add(t.getQuantity().multiply(t.getPrice()));
            } else {
                BigDecimal averagePrice = totalCost.divide(quantity, 4, RoundingMode.HALF_UP);
                BigDecimal profit = t.getPrice().subtract(averagePrice).multiply(t.getQuantity());
                realizedProfit = realizedProfit.add(profit);
                quantity = quantity.subtract(t.getQuantity());
                totalCost = totalCost.subtract(t.getQuantity().multiply(averagePrice));

            }
        }

        if (quantity.compareTo(BigDecimal.ZERO) > 0) {
            finalAveragePrice = totalCost.divide(quantity, 4, RoundingMode.HALF_UP);
        }

        return new Holding(ticker, quantity, finalAveragePrice, realizedProfit, BigDecimal.ZERO);
    }

    private BigDecimal costOf(Holding h) {
        return h.getQuantity().multiply(h.getAveragePrice());
    }

    private Holding withWeight(Holding h, BigDecimal total) {
        BigDecimal weight = costOf(h).multiply(BigDecimal.valueOf(100)).divide(total, 2, RoundingMode.HALF_UP);
        return new Holding(h.getAssetTicker(), h.getQuantity(), h.getAveragePrice(),
                h.getRealizedProfit(), weight);
    }
}