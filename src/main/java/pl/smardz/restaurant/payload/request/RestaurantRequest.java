package pl.smardz.restaurant.payload.request;

import lombok.*;
import pl.smardz.restaurant.enums.Unit;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRequest {
    private BigDecimal x;
    private BigDecimal y;
    private Unit unit = Unit.KILOMETERS;
    private String foodType;
    private Integer page;
}
