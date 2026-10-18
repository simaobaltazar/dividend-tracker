package pt.simaobaltazar.dividendtracker.service;

import org.springframework.stereotype.Service;
import pt.simaobaltazar.dividendtracker.model.Transaction;
import pt.simaobaltazar.dividendtracker.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {

    private final List<Transaction> transactions = List.of(
            new Transaction(1L, TransactionType.BUY, new BigDecimal("10"),
                    new BigDecimal("172.50"), LocalDate.of(2026, 1, 15), "AAPL"),
            new Transaction(2L, TransactionType.BUY, new BigDecimal("200"),
                    new BigDecimal("3.85"), LocalDate.of(2026, 2, 3), "EDP.LS"),
            new Transaction(3L, TransactionType.BUY, new BigDecimal("2.5"),
                    new BigDecimal("118.20"), LocalDate.of(2026, 3, 10), "VWCE"),
            new Transaction(4L, TransactionType.SELL, new BigDecimal("4"),
                    new BigDecimal("190.10"), LocalDate.of(2026, 6, 20), "AAPL")
    );

    public List<Transaction> findAll() {
        return transactions;
    }
}
