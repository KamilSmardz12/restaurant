package pl.smardz.restaurant.payload.request;

import liquibase.pro.packaged.B;
import lombok.*;
import pl.smardz.restaurant.enums.Unit;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRequestDataToFindRestaurant {
    @NonNull
    private BigDecimal x;
    @NonNull
    private BigDecimal y;
    private Unit unit = Unit.KILOMETERS;
    private String foodType;
    private int pageNr;
}
