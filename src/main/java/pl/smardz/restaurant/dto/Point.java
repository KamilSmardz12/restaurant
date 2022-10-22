package pl.smardz.restaurant.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@EqualsAndHashCode
@Getter
@Builder
public class Point {
    private final BigDecimal x;
    private final BigDecimal y;
}
