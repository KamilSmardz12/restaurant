package pl.smardz.restaurant.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public enum Unit {
    MILES("M", BigDecimal.valueOf(1)),
    KILOMETERS("K", BigDecimal.valueOf(1.609344)),
    NAUTICAL("N", BigDecimal.valueOf(0.8684));

    private final String unitShortcut;
    private final BigDecimal multiplier;
}
