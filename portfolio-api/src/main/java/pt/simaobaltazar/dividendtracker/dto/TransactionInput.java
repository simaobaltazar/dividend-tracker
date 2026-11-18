package pt.simaobaltazar.dividendtracker.dto;

import pt.simaobaltazar.dividendtracker.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionInput {

    private final TransactionType type;
    private final BigDecimal quantity;
    private final BigDecimal price;
    private final LocalDate date;
    private final String assetTicker;

    public TransactionInput(TransactionType type, BigDecimal quantity, BigDecimal price, LocalDate date,
                            String assetTicker) {
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
        this.assetTicker = assetTicker;
    }

    public TransactionType getType() { return type; }

    public BigDecimal getQuantity() { return quantity; }

    public BigDecimal getPrice() { return price; }

    public LocalDate getDate() { return date; }

    public String getAssetTicker() { return assetTicker; }
}
