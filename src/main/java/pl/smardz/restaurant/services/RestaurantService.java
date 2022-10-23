package pl.smardz.restaurant.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.smardz.restaurant.exceptions.RequestDataIsIncomplete;
import pl.smardz.restaurant.mappers.RepoMapper;
import pl.smardz.restaurant.model.Restaurant;
import pl.smardz.restaurant.payload.request.RestaurantRequest;
import pl.smardz.restaurant.payload.request.RestaurantSaveRequest;
import pl.smardz.restaurant.payload.response.RestaurantData;
import pl.smardz.restaurant.repository.RestaurantRepo;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RestaurantService {
    private static final int PAGE_SIZE = 5;
    private final RestaurantRepo repo;

    @Transactional
    public Optional<RestaurantData> save(RestaurantSaveRequest restaurantData) {
        return Optional.of(repo.save(RepoMapper.map(restaurantData)))
                .map(RepoMapper.ResponseMapper::mapToRestaurantData);
    }

    public List<RestaurantData> findRestaurants(RestaurantRequest restaurantRequest) {
        validateTheCoordinateValue(restaurantRequest.getX(), "X");
        validateTheCoordinateValue(restaurantRequest.getY(), "Y");

        return findRestaurantsWithDistance(restaurantRequest)
                .stream()
                .map(RepoMapper.ResponseMapper::mapToRestaurantData)
                .collect(Collectors.toList());
    }

    private BigDecimal validateTheCoordinateValue(BigDecimal coordinateValue, String coordinateName) {
        return Optional.ofNullable(coordinateValue)
                .orElseThrow(() -> new RequestDataIsIncomplete("Invalid value for " + coordinateName));
    }

    private List<Restaurant> findRestaurantsWithDistance(RestaurantRequest restaurantRequest) {
        return repo.findAllWithDistance(
                restaurantRequest.getX(),
                restaurantRequest.getY(),
                PAGE_SIZE,
                calculateOffset(restaurantRequest)
        );
    }

    private int calculateOffset(RestaurantRequest restaurantRequest) {
        int offset = restaurantRequest.getPage() > 0 ? restaurantRequest.getPage() : 1;
        return PAGE_SIZE * offset;
    }

}
