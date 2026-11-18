package pt.simaobaltazar.dividendtracker.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pt.simaobaltazar.dividendtracker.model.Asset;
import pt.simaobaltazar.dividendtracker.model.Transaction;
import pt.simaobaltazar.dividendtracker.model.TransactionType;
import pt.simaobaltazar.dividendtracker.repository.AssetRepository;
import pt.simaobaltazar.dividendtracker.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final AssetRepository assetRepository;
    private final TransactionRepository transactionRepository;

    public DataSeeder(AssetRepository assetRepository, TransactionRepository transactionRepository) {
        this.assetRepository = assetRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void run(String... args) {
        if (assetRepository.count() > 0) {
            return;
        }

        assetRepository.saveAll(List.of(
                new Asset("AAPL", "Apple Inc.", "Technology", "US"),
                new Asset("EDP.LS", "EDP - Energias de Portugal", "Utilities", "PT"),
                new Asset("VWCE.DE", "Vanguard FTSE All-World ETF", null, "IE")));

        transactionRepository.saveAll(List.of(
                new Transaction(null, TransactionType.BUY, new BigDecimal("10"),
                        new BigDecimal("172.50"), LocalDate.of(2026, 1, 15), "AAPL"),
                new Transaction(null, TransactionType.BUY, new BigDecimal("200"),
                        new BigDecimal("3.85"), LocalDate.of(2026, 2, 3), "EDP.LS"),
                new Transaction(null, TransactionType.BUY, new BigDecimal("2.5"),
                        new BigDecimal("118.20"), LocalDate.of(2026, 3, 10), "VWCE.DE"),
                new Transaction(null, TransactionType.SELL, new BigDecimal("4"),
                        new BigDecimal("190.10"), LocalDate.of(2026, 6, 20), "AAPL")));
    }
}