package pt.simaobaltazar.dividendtracker.model;

import java.util.List;

public class TransactionPage {

    private final List<Transaction> content;
    private final int page;
    private final int size;
    private final int totalElements;
    private final int totalPages;

    public TransactionPage(List<Transaction> content, int page, int size, int totalElements, int totalPages) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public List<Transaction> getContent() { return content; }

    public int getPage() { return page; }

    public int getSize() { return size; }

    public int getTotalElements() { return totalElements; }

    public int getTotalPages() { return totalPages; }
}
