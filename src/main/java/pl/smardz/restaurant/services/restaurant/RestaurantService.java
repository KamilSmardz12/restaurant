package pl.smardz.restaurant.services.restaurant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.smardz.restaurant.mappers.RepoMapper;
import pl.smardz.restaurant.model.FoodType;
import pl.smardz.restaurant.model.Restaurant;
import pl.smardz.restaurant.payload.request.RestaurantRequest;
import pl.smardz.restaurant.payload.request.RestaurantSaveRequest;
import pl.smardz.restaurant.payload.response.RestaurantData;
import pl.smardz.restaurant.repository.FoodTypeRepo;
import pl.smardz.restaurant.repository.RestaurantRepo;
import pl.smardz.restaurant.services.calculators.OffsetCalculator;
import pl.smardz.restaurant.validators.CoordinatesValidator;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class RestaurantService {
    private static final int PAGE_SIZE = 5;
    private final RestaurantRepo restaurantRepo;
    private final FoodTypeRepo foodTypeRepo;
    private final CoordinatesValidator coordinatesValidator;
    private final OffsetCalculator offsetCalculator;

    @Transactional
    public Optional<RestaurantData> save(RestaurantSaveRequest restaurantData) {
        return Optional.of(restaurantRepo.save(RepoMapper.map(restaurantData)))
                .map(RepoMapper.ResponseMapper::mapToRestaurantData);
    }


    public List<RestaurantData> findRestaurants(RestaurantRequest restaurantRequest) {
        coordinatesValidator.validateTheXCoordinateValue(restaurantRequest.getX());
        coordinatesValidator.validateTheYCoordinateValue(restaurantRequest.getY());

        log.info("Start of downloading restaurant data: " + LocalDateTime.now());
        final List<Restaurant> restaurants = getRestaurantsWithFoodTypes(restaurantRequest);
        log.info("End of restaurant data download: " + LocalDateTime.now());

        log.info("The restaurant data has been correctly retrieved");

        return restaurants
                .stream()
                .map(RepoMapper.ResponseMapper::mapToRestaurantData)
                .collect(Collectors.toList());
    }

    private List<Restaurant> getRestaurantsWithFoodTypes(RestaurantRequest restaurantRequest) {
        final List<Restaurant> restaurants = findRestaurantsWithDistance(restaurantRequest);
        final List<FoodType> foodTypes = foodTypeRepo.findAllByRestaurantIdIn(getRestaurantIds(restaurants));

        restaurants.forEach(restaurant -> restaurant.setFoodTypes(extractFoodType(foodTypes, restaurant.getId())));

        return restaurants;
    }

    private Set<FoodType> extractFoodType(List<FoodType> foodTypes, Long restaurantId) {
        return foodTypes.stream()
                .filter(foodType -> foodType.getFoodId().equals(restaurantId))
                .collect(Collectors.toSet());

    }

    private List<Long> getRestaurantIds(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(Restaurant::getId)
                .collect(Collectors.toList());
    }

    private List<Restaurant> findRestaurantsWithDistance(RestaurantRequest restaurantRequest) {
        return restaurantRepo.findAllWithDistance(
                restaurantRequest.getX(),
                restaurantRequest.getY(),
                PAGE_SIZE,
                offsetCalculator.calculateOffset(restaurantRequest, PAGE_SIZE),
                restaurantRequest.getUnit().getMultiplier(),
                determineValueOfFoodType(restaurantRequest)
        );
    }

    private String determineValueOfFoodType(RestaurantRequest restaurantRequest) {
        return Optional.ofNullable(restaurantRequest.getFoodType())
                .orElse("");
    }

}
