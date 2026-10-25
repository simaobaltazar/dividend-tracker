package pt.simaobaltazar.dividendtracker.model;

import java.util.List;

public record TransactionPage(
        List<Transaction> content,
        int page,
        int size,
        int totalElements,
        int totalPages
) {}