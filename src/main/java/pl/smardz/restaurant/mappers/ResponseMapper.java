package pl.smardz.restaurant.mappers;

import org.springframework.stereotype.Component;
import pl.smardz.restaurant.dto.Distance;
import pl.smardz.restaurant.dto.Point;
import pl.smardz.restaurant.enums.Unit;
import pl.smardz.restaurant.model.FoodType;
import pl.smardz.restaurant.model.Restaurant;
import pl.smardz.restaurant.payload.response.RestaurantData;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ResponseMapper {
    public RestaurantData mapToRestaurantData(Restaurant restaurant) {
        return RestaurantData
                .builder()
                .name(restaurant.getName())
                .restaurantPoint(Point.builder()
                        .x(restaurant.getX())
                        .y(restaurant.getY())
                        .build())
                .distance(Distance.builder()
                        .distance(restaurant.getDistance())
                        .unit(Unit.KILOMETERS)
                        .build())
                .foodType(mapFoodTypes(restaurant.getFoodTypes()))
                .build();
    }

    private Set<String> mapFoodTypes(Set<FoodType> foodTypes) {
        return foodTypes.stream()
                .map(FoodType::getFoodType)
                .collect(Collectors.toSet());
    }
}