package pl.smardz.restaurant.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.smardz.restaurant.calculators.distance.DistanceCalculator;
import pl.smardz.restaurant.dto.Distance;
import pl.smardz.restaurant.dto.Point;
import pl.smardz.restaurant.model.Restaurant;
import pl.smardz.restaurant.payload.request.RestaurantRequestDataToFindRestaurant;
import pl.smardz.restaurant.payload.request.RestaurantRequestDataToSave;
import pl.smardz.restaurant.payload.response.FindedRestaurantData;
import pl.smardz.restaurant.payload.response.ResponseMapper;
import pl.smardz.restaurant.repository.RestaurantRepo;
import pl.smardz.restaurant.repository.mappers.RepoMapper;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RestaurantService {
    private final RestaurantRepo repo;


    @Transactional
    public Optional<Restaurant> save(RestaurantRequestDataToSave restaurantData) {
        return Optional.of(repo.save(RepoMapper.map(restaurantData)));
    }

    public Set<FindedRestaurantData> findAll(RestaurantRequestDataToFindRestaurant restaurantRequest) {
        return repo.findAll(
                        restaurantRequest.getFoodType(),
                        restaurantRequest.getX(),
                        restaurantRequest.getY()
                ).stream()
                .map(getRestaurantFindedRestaurantDataFunction(restaurantRequest))
                .collect(Collectors.toSet());
    }

    private Function<Restaurant, FindedRestaurantData> getRestaurantFindedRestaurantDataFunction(RestaurantRequestDataToFindRestaurant restaurantRequest) {
        return restaurant -> {
            final Point restaurantPoint = Point.builder()
                    .x(restaurant.getX())
                    .y(restaurant.getY())
                    .build();
            final Point requestPoint = Point.builder()
                    .x(restaurantRequest.getX())
                    .y(restaurantRequest.getY())
                    .build();
            final Distance distance = DistanceCalculator.calculate(restaurantPoint, requestPoint, restaurantRequest.getUnit());

            return ResponseMapper.mapToFindedRestaurantData(restaurant, distance);
        };
    }

}
