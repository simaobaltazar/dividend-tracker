package pt.simaobaltazar.dividendtracker.controller;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import pt.simaobaltazar.dividendtracker.model.Asset;
import pt.simaobaltazar.dividendtracker.model.Transaction;
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
    public List<Transaction> transactions() {
        return transactionService.findAll();
    }

    @SchemaMapping
    public Asset asset(Transaction transaction) {
        return assetService.findByTicker(transaction.assetTicker());
    }
}