package pt.simaobaltazar.dividendtracker.model;

import java.math.BigDecimal;

public record DripYear(int year,
                       BigDecimal grossDividend,
                       BigDecimal tax,
                       BigDecimal netDividend,
                       BigDecimal sharesBought,
                       BigDecimal shares,
                       BigDecimal positionValue) {
}
