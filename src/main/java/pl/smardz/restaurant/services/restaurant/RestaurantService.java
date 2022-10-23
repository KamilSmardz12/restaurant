package pl.smardz.restaurant.services.restaurant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.smardz.restaurant.mappers.RepoMapper;
import pl.smardz.restaurant.model.Restaurant;
import pl.smardz.restaurant.payload.request.RestaurantRequest;
import pl.smardz.restaurant.payload.request.RestaurantSaveRequest;
import pl.smardz.restaurant.payload.response.RestaurantData;
import pl.smardz.restaurant.repository.RestaurantRepo;
import pl.smardz.restaurant.services.calculators.OffsetCalculator;
import pl.smardz.restaurant.validators.CoordinatesValidator;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class RestaurantService {
    private static final int PAGE_SIZE = 5;
    private final RestaurantRepo repo;
    private final CoordinatesValidator coordinatesValidator;
    private final OffsetCalculator offsetCalculator;

    @Transactional
    public Optional<RestaurantData> save(RestaurantSaveRequest restaurantData) {
        return Optional.of(repo.save(RepoMapper.map(restaurantData)))
                .map(RepoMapper.ResponseMapper::mapToRestaurantData);
    }


    public List<RestaurantData> findRestaurants(RestaurantRequest restaurantRequest) {
        coordinatesValidator.validateTheXCoordinateValue(restaurantRequest.getX());
        coordinatesValidator.validateTheYCoordinateValue(restaurantRequest.getY());

        log.info("Start of downloading restaurant data: " + LocalDateTime.now());
        final List<Restaurant> restaurants = findRestaurantsWithDistance(restaurantRequest);
        log.info("End of restaurant data download: " + LocalDateTime.now());

        log.info("The restaurant data has been correctly retrieved");

        return restaurants
                .stream()
                .map(RepoMapper.ResponseMapper::mapToRestaurantData)
                .collect(Collectors.toList());
    }

    private List<Restaurant> findRestaurantsWithDistance(RestaurantRequest restaurantRequest) {
        return repo.findAllWithDistance(
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
