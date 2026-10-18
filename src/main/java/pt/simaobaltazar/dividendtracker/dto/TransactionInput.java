package pt.simaobaltazar.dividendtracker.dto;

import pt.simaobaltazar.dividendtracker.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionInput(TransactionType type,
                               BigDecimal quantity,
                               BigDecimal price,
                               LocalDate date,
                               String assetTicker
) {
}
