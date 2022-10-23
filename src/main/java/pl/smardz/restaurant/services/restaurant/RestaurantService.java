package pl.smardz.restaurant.services.restaurant;

import lombok.RequiredArgsConstructor;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RestaurantService {
    private static final int PAGE_SIZE = 55;
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

        return findRestaurantsWithDistance(restaurantRequest)
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
                determineValueOfFoodType(restaurantRequest)
        );
    }

    private String determineValueOfFoodType(RestaurantRequest restaurantRequest) {
        return Optional.ofNullable(restaurantRequest.getFoodType())
                .orElse("");
    }

}
