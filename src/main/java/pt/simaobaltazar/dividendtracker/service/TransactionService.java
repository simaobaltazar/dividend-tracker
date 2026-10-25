package pt.simaobaltazar.dividendtracker.service;

import org.springframework.stereotype.Service;
import pt.simaobaltazar.dividendtracker.dto.TransactionInput;
import pt.simaobaltazar.dividendtracker.model.Transaction;
import pt.simaobaltazar.dividendtracker.model.TransactionPage;
import pt.simaobaltazar.dividendtracker.model.TransactionType;
import pt.simaobaltazar.dividendtracker.service.exceptions.InvalidRequestException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TransactionService {

    private final List<Transaction> transactions = new ArrayList<>(List.of(
            new Transaction(1L, TransactionType.BUY, new BigDecimal("10"),
                    new BigDecimal("172.50"), LocalDate.of(2026, 1, 15), "AAPL"),
            new Transaction(2L, TransactionType.BUY, new BigDecimal("200"),
                    new BigDecimal("3.85"), LocalDate.of(2026, 2, 3), "EDP.LS"),
            new Transaction(3L, TransactionType.BUY, new BigDecimal("2.5"),
                    new BigDecimal("118.20"), LocalDate.of(2026, 3, 10), "VWCE"),
            new Transaction(4L, TransactionType.SELL, new BigDecimal("4"),
                    new BigDecimal("190.10"), LocalDate.of(2026, 6, 20), "AAPL")
    ));

    private final AtomicLong idGenerator = new AtomicLong(4);

    private final AssetService assetService;

    public TransactionService(AssetService assetService) {
        this.assetService = assetService;
    }

    public List<Transaction> findAll() {
        return transactions;
    }

    public Transaction add(TransactionInput input) {
        assetService.findByTicker(input.assetTicker());

        if (input.quantity().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidRequestException("Quantity must be positive");
        }
        if (input.price().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidRequestException("Price must be positive");
        }

        LocalDate lastDate = transactions.stream()
                .filter(t -> t.assetTicker().equals(input.assetTicker()))
                .map(Transaction::date)
                .max(Comparator.naturalOrder())
                .orElse(LocalDate.MIN);

        if (input.date().isBefore(lastDate)) {
            throw new InvalidRequestException(
                    "Transaction date cannot be before the last transaction of this asset");
        }

        if (input.type() == TransactionType.SELL
                && input.quantity().compareTo(quantityHeld(input.assetTicker())) > 0) {
            throw new InvalidRequestException("Not enough shares to sell");
        }

        Transaction transaction = new Transaction(
                idGenerator.incrementAndGet(),
                input.type(),
                input.quantity(),
                input.price(),
                input.date(),
                input.assetTicker()
        );
        transactions.add(transaction);
        return transaction;
    }

    public TransactionPage findPage(int page, int size) {

        if (page < 0) {
            throw new InvalidRequestException("Page must be zero or positive");
        }
        if (size < 1) {
            throw new InvalidRequestException("Size must be positive");
        }

        List<Transaction> content = transactions.stream().sorted(Comparator
                .comparing(Transaction::date).reversed()).skip((long) page * size).limit(size).toList();

        int totalElements = transactions.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        return new TransactionPage(content, page, size, totalElements, totalPages);
    }

    public boolean delete(Long id) {
        return transactions.removeIf(t -> t.id().equals(id));
    }

    private BigDecimal quantityHeld(String ticker) {
        BigDecimal quantity = BigDecimal.ZERO;

        for (Transaction t : transactions) {
            if (!t.assetTicker().equals(ticker)) {
                continue;
            }
            if (t.type() == TransactionType.BUY) {
                quantity = quantity.add(t.quantity());
            } else {
                quantity = quantity.subtract(t.quantity());
            }
        }

        return quantity;
    }
}