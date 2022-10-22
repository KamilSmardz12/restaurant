package pl.smardz.restaurant.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRequestDataToSave {
    @NonNull
    private String name;
    @NonNull
    private BigDecimal x;
    @NonNull
    private BigDecimal y;
    @NonNull
    private String foodType;
}
