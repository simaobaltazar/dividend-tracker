package pt.simaobaltazar.dividendtracker.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import pt.simaobaltazar.dividendtracker.dto.TransactionInput;
import pt.simaobaltazar.dividendtracker.model.Asset;
import pt.simaobaltazar.dividendtracker.model.Transaction;
import pt.simaobaltazar.dividendtracker.model.TransactionPage;
import pt.simaobaltazar.dividendtracker.service.AssetService;
import pt.simaobaltazar.dividendtracker.service.TransactionService;

import java.util.List;

@Controller
public class TransactionController {

    private final TransactionService transactionService;
    private final AssetService assetService;

    public TransactionController(TransactionService transactionService, AssetService assetService) {
        this.transactionService = transactionService;
        this.assetService = assetService;
    }

    @QueryMapping
    public TransactionPage transactions(@Argument int page, @Argument int size) {
        return transactionService.findPage(page, size);
    }

    @SchemaMapping
    public Asset asset(Transaction transaction) {
        return assetService.findByTicker(transaction.assetTicker());
    }

    @MutationMapping
    public Transaction addTransaction(@Argument TransactionInput newTransaction) {
        return transactionService.add(newTransaction);
    }

    @MutationMapping
    public boolean deleteTransaction(@Argument Long id) {
        return transactionService.delete(id);
    }
}