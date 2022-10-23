package pl.smardz.restaurant.repository.mappers;

import pl.smardz.restaurant.model.FoodType;
import pl.smardz.restaurant.model.Restaurant;
import pl.smardz.restaurant.payload.request.RestaurantRequestDataToSave;

import java.util.HashSet;
import java.util.Set;

public class RepoMapper {

    private RepoMapper() {
    }

    public static Restaurant map(RestaurantRequestDataToSave restaurantData) {
        Set<FoodType> foodTypes = new HashSet<>();
        FoodType foodType = createFoodType(restaurantData);
        foodTypes.add(foodType);

        Restaurant build = Restaurant.builder()//
                .name(restaurantData.getName())
                .x(restaurantData.getX())
                .y(restaurantData.getY())
                .foodTypes(foodTypes)
                .build();

//        foodType.setRestaurant(build);
        return build;
    }

    private static FoodType createFoodType(RestaurantRequestDataToSave restaurantData) {
        FoodType foodType = new FoodType();
        foodType.setFoodType(restaurantData.getFoodType());
        return foodType;
    }
}
