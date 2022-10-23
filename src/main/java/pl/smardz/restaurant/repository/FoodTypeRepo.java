package pl.smardz.restaurant.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.smardz.restaurant.model.FoodType;

import java.util.List;

@Repository
public interface FoodTypeRepo extends CrudRepository<FoodType, Long> {
    List<FoodType> findAllByRestaurantIdIn(List<Long> ids);
}
