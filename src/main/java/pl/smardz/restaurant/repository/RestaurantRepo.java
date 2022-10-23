package pl.smardz.restaurant.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.smardz.restaurant.model.Restaurant;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RestaurantRepo extends CrudRepository<Restaurant, Long> {
    @Query(value = "" +
            " SELECT" +
            "  R.RESTAURANT_ID," +
            "  NAME," +
            "  X, " +
            "  Y, " +
            "  (" +
            "  DEGREES(" +
            "          acos" +
            "              (" +
            "                sin(radians(X)) * sin(radians(:PM_X)) + cos(radians(X))" +
            "                * cos(radians(:PM_X)) * cos(radians(Y-:PM_Y)) " +
            "              )" +
            "          * 60 * 1.1515 * :PM_UNIT_MULTIPLIER) " +
            "  ) distance, " +
            "  (SELECT 1 FROM FOOD_TYPES FT WHERE FT.RESTAURANT_ID = R.RESTAURANT_ID AND FOOD_TYPE = :PM_FOOD_TYPE) IS_CORRECT_FOOD_TYPE" +
            " FROM RESTAURANTS R" +
            "  WHERE EXISTS (SELECT 1 FROM FOOD_TYPES FT WHERE FT.RESTAURANT_ID = R.RESTAURANT_ID)"+
            "  ORDER BY IS_CORRECT_FOOD_TYPE desc, distance" +
            "  LIMIT :PM_LIMIT" +
            "  OFFSET :PM_OFFSET", nativeQuery = true)
    List<Restaurant> findAllWithDistance(
            @Param(value = "PM_X") BigDecimal x,
            @Param(value = "PM_Y") BigDecimal y,
            @Param(value = "PM_LIMIT") Integer limit,
            @Param(value = "PM_OFFSET") Integer offset,
            @Param(value = "PM_UNIT_MULTIPLIER") BigDecimal unitMultiplier,
            @Param(value = "PM_FOOD_TYPE") String foodType
    );
}
