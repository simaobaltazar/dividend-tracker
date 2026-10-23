package pt.simaobaltazar.dividendtracker.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Transaction(Long id,
                          TransactionType type,
                          BigDecimal quantity,
                          BigDecimal price,
                          LocalDate date,
                          String assetTicker
) {
}
