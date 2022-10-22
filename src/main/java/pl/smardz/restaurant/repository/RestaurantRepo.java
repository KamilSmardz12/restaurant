package pl.smardz.restaurant.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.smardz.restaurant.model.Restaurant;

import java.math.BigDecimal;
import java.util.Set;

@Repository
public interface RestaurantRepo extends CrudRepository<Restaurant, Long> {
    @Query(value = "" +
            " SELECT" +
            " *" +
            " FROM FOOD_TYPES FT" +
            " JOIN RESTAURANTS R ON FT.RESTAURANT_ID = R.RESTAURANT_ID"
//            +
//            " WHERE" +
//            "    FT.FOOD_TYPE = :PM_FOOD_TYPE"+
//            "    AND R.X = :PM_X"+
//            "    AND R.Y = :PM_Y"
            , nativeQuery = true)
    Set<Restaurant> findAll(
            @Param(value = "PM_FOOD_TYPE") String foodType,
            @Param(value = "PM_X") BigDecimal x,
            @Param(value = "PM_Y") BigDecimal y
    );

}
