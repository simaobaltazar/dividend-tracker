package pt.simaobaltazar.dividendtracker.service;

import org.springframework.data.domain.Page;          // novo
import org.springframework.data.domain.PageRequest;   // novo
import org.springframework.data.domain.Sort;          // novo
import org.springframework.stereotype.Service;
import pt.simaobaltazar.dividendtracker.dto.TransactionInput;
import pt.simaobaltazar.dividendtracker.model.Transaction;
import pt.simaobaltazar.dividendtracker.model.TransactionPage;
import pt.simaobaltazar.dividendtracker.model.TransactionType;
import pt.simaobaltazar.dividendtracker.repository.TransactionRepository;
import pt.simaobaltazar.dividendtracker.service.exceptions.InvalidRequestException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AssetService assetService;

    public TransactionService(TransactionRepository transactionRepository, AssetService assetService) {
        this.transactionRepository = transactionRepository;
        this.assetService = assetService;
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public Transaction add(TransactionInput input) {
        assetService.findByTicker(input.getAssetTicker());

        if (input.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidRequestException("Quantity must be positive");
        }
        if (input.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidRequestException("Price must be positive");
        }

        LocalDate lastDate = transactionRepository.findByAssetTicker(input.getAssetTicker()).stream()
                .map(Transaction::getDate)
                .max(Comparator.naturalOrder())
                .orElse(LocalDate.MIN);

        if (input.getDate().isBefore(lastDate)) {
            throw new InvalidRequestException(
                    "Transaction date cannot be before the last transaction of this asset");
        }

        if (input.getType() == TransactionType.SELL
                && input.getQuantity().compareTo(quantityHeld(input.getAssetTicker())) > 0) {
            throw new InvalidRequestException("Not enough shares to sell");
        }

        Transaction transaction = new Transaction(null, input.getType(), input.getQuantity(),
                input.getPrice(), input.getDate(), input.getAssetTicker());
        return transactionRepository.save(transaction);
    }

    public TransactionPage findPage(int page, int size) {
        if (page < 0) {
            throw new InvalidRequestException("Page must be zero or positive");
        }
        if (size < 1) {
            throw new InvalidRequestException("Size must be positive");
        }

        Page<Transaction> result = transactionRepository.findAll(
                PageRequest.of(page, size, Sort.by("date").descending()));

        return new TransactionPage(result.getContent(), page, size,
                (int) result.getTotalElements(), result.getTotalPages());
    }

    public boolean delete(Long id) {

        if (!transactionRepository.existsById(id)) {
            return false;
        }
        transactionRepository.deleteById(id);
        return true;
    }

    private BigDecimal quantityHeld(String ticker) {
        BigDecimal quantity = BigDecimal.ZERO;

        for (Transaction t : transactionRepository.findByAssetTicker(ticker)) {
            if (t.getType() == TransactionType.BUY) {
                quantity = quantity.add(t.getQuantity());
            } else {
                quantity = quantity.subtract(t.getQuantity());
            }
        }

        return quantity;
    }
}