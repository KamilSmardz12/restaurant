package pl.smardz.restaurant.mappers;

import pl.smardz.restaurant.dto.Distance;
import pl.smardz.restaurant.dto.Point;
import pl.smardz.restaurant.enums.Unit;
import pl.smardz.restaurant.model.FoodType;
import pl.smardz.restaurant.model.Restaurant;
import pl.smardz.restaurant.payload.request.RestaurantSaveRequest;
import pl.smardz.restaurant.payload.response.RestaurantData;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class RepoMapper {

    private RepoMapper() {
    }

    //TODO
    public static Restaurant map(RestaurantSaveRequest restaurantData) {
        Set<FoodType> foodTypes = new HashSet<>();
        FoodType foodType = createFoodType(restaurantData);
        foodTypes.add(foodType);

        return Restaurant.builder()//
                .name(restaurantData.getName())
                .x(restaurantData.getX())
                .y(restaurantData.getY())
                .foodTypes(foodTypes)
                .build();
    }

    private static FoodType createFoodType(RestaurantSaveRequest restaurantData) {
        FoodType foodType = new FoodType();
        foodType.setFoodType(restaurantData.getFoodType());
        return foodType;
    }

    public static class ResponseMapper {
        private ResponseMapper() {
        }

        public static RestaurantData mapToRestaurantData(Restaurant restaurant) {
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

        private static Set<String> mapFoodTypes(Set<FoodType> foodTypes) {
            return foodTypes.stream()
                    .map(FoodType::getFoodType)
                    .collect(Collectors.toSet());
        }
    }
}
