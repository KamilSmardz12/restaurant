package pl.smardz.restaurant.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.smardz.restaurant.model.Restaurant;
import pl.smardz.restaurant.payload.request.RestaurantRequestDataToFindRestaurant;
import pl.smardz.restaurant.payload.request.RestaurantRequestDataToSave;
import pl.smardz.restaurant.payload.response.FindedRestaurantData;
import pl.smardz.restaurant.payload.response.ResponseMapper;
import pl.smardz.restaurant.repository.RestaurantRepo;
import pl.smardz.restaurant.repository.mappers.RepoMapper;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RestaurantService {
    private static final int PAGE_SIZE = 5;
    private final RestaurantRepo repo;

    @Transactional
    public Optional<Restaurant> save(RestaurantRequestDataToSave restaurantData) {
        return Optional.of(repo.save(RepoMapper.map(restaurantData)));
    }

    public Set<FindedRestaurantData> findAll(RestaurantRequestDataToFindRestaurant restaurantRequest) {
        return findAllWithDistance(restaurantRequest)
                .stream()
                .map(ResponseMapper::mapToFindedRestaurantData)
                .collect(Collectors.toSet());
    }

    private List<Restaurant> findAllWithDistance(RestaurantRequestDataToFindRestaurant restaurantRequest) {
        return repo.findAllWithDistance(
                restaurantRequest.getX(),
                restaurantRequest.getY(),
                PAGE_SIZE,
                calculateOffset(restaurantRequest)
        );
    }

    private int calculateOffset(RestaurantRequestDataToFindRestaurant restaurantRequest) {
        int offset = restaurantRequest.getPageNr() > 0 ? restaurantRequest.getPageNr() : 1;
        return PAGE_SIZE * offset;
    }


}
