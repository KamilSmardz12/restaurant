package pl.smardz.restaurant.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.smardz.restaurant.dto.Distance;
import pl.smardz.restaurant.dto.Point;

import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindedRestaurantData {
    private String name;
    private Point restaurantPoint;
    private Distance distance;
    private Set<String> foodType;
}
