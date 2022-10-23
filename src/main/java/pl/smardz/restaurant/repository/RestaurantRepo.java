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
            "  NAME," +
            "  X, " +
            "  Y, " +
            "  FOOD_ID, " +
            "  FOOD_TYPE, " +
            "  R.RESTAURANT_ID," +
            "  (" +
            "  DEGREES(" +
            "          acos" +
            "              (" +
            "                sin(radians(X)) * sin(radians(:PM_X)) + cos(radians(X))" +
            "                * cos(radians(:PM_X)) * cos(radians(Y-:PM_Y)) " +
            "              )" +
            "          * 60 * 1.1515 * 1.609344) " +
            //TODO add conversion to different units
            "  ) distance" +
            " FROM RESTAURANTS R" +
            " JOIN FOOD_TYPES FT ON FT.RESTAURANT_ID = R.RESTAURANT_ID" +
            "  ORDER BY " +
            " distance" +
            " LIMIT :PM_LIMIT" +
            " OFFSET :PM_OFFSET", nativeQuery = true)
    List<Restaurant> findAllWithDistance(
            @Param(value = "PM_X") BigDecimal x,
            @Param(value = "PM_Y") BigDecimal y,
            @Param(value = "PM_LIMIT") Integer limit,
            @Param(value = "PM_OFFSET") Integer offset
    );
}
