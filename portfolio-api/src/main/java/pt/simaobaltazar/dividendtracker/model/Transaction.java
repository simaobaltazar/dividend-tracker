package pt.simaobaltazar.dividendtracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal quantity;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal price;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String assetTicker;

    protected Transaction() {
    }

    public Transaction(Long id, TransactionType type, BigDecimal quantity, BigDecimal price,
                       LocalDate date, String assetTicker) {
        this.id = id;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
        this.assetTicker = assetTicker;
    }

    public Long getId() { return id; }

    public TransactionType getType() { return type; }

    public BigDecimal getQuantity() { return quantity; }

    public BigDecimal getPrice() { return price; }

    public LocalDate getDate() { return date; }

    public String getAssetTicker() { return assetTicker; }
}
