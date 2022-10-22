package pl.smardz.restaurant.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import pl.smardz.restaurant.enums.Unit;

import java.math.BigDecimal;

@EqualsAndHashCode
@Getter
@Builder
public class Distance {
    private final BigDecimal distance;
    private final Unit unit;
}
