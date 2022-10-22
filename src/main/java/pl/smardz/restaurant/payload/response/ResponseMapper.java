package pl.smardz.restaurant.payload.response;

import pl.smardz.restaurant.dto.Distance;
import pl.smardz.restaurant.dto.Point;
import pl.smardz.restaurant.model.FoodType;
import pl.smardz.restaurant.model.Restaurant;

import java.util.Set;
import java.util.stream.Collectors;

public class ResponseMapper {
    public static FindedRestaurantData mapToFindedRestaurantData(Restaurant restaurant, Distance distance) {
        return FindedRestaurantData
                .builder()
                .name(restaurant.getName())
                .restaurantPoint(Point.builder()
                        .x(restaurant.getX())
                        .y(restaurant.getY())
                        .build())
                .distance(distance)
                .foodType(mapFoodTypes(restaurant.getFoodTypes()))
                .build();
    }


    private static Set<String> mapFoodTypes(Set<FoodType> foodTypes) {
        return foodTypes.stream()
                .map(FoodType::getFoodType)
                .collect(Collectors.toSet());
    }
}
