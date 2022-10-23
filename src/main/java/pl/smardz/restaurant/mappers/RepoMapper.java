package pl.smardz.restaurant.mappers;

import org.springframework.stereotype.Component;
import pl.smardz.restaurant.model.FoodType;
import pl.smardz.restaurant.model.Restaurant;
import pl.smardz.restaurant.payload.request.RestaurantSaveRequest;

import java.util.HashSet;
import java.util.Set;

@Component
public class RepoMapper {

    public Restaurant map(RestaurantSaveRequest restaurantData) {
        return Restaurant.builder()//
                .name(restaurantData.getName())
                .x(restaurantData.getX())
                .y(restaurantData.getY())
                .foodTypes(createFoodtypes(restaurantData))
                .build();
    }

    private Set<FoodType> createFoodtypes(RestaurantSaveRequest restaurantData) {
        Set<FoodType> foodTypes = new HashSet<>();
        foodTypes.add(createFoodType(restaurantData));

        return foodTypes;
    }

    private FoodType createFoodType(RestaurantSaveRequest restaurantData) {
        FoodType foodType = new FoodType();
        foodType.setFoodType(restaurantData.getFoodType());
        return foodType;
    }

}
