package pl.smardz.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "FOOD_TYPES")
public class FoodType {
    @Id
    @GeneratedValue(generator = "SEK_FOOD_TYPE")
    @Column(name = "food_id")
    private Long foodId;

    @Column(name = "food_type")
    private String foodType;

    @Column(name = "restaurant_id")
    private Long restaurantId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FoodType foodType = (FoodType) o;
        return foodId != null && Objects.equals(foodId, foodType.foodId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
