package pt.simaobaltazar.dividendtracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.simaobaltazar.dividendtracker.model.Transaction;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAssetTicker(String assetTicker);
}