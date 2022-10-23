package pl.smardz.restaurant.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.lang.NonNull;
import pl.smardz.restaurant.annotation.Transient;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "RESTAURANTS")
public class Restaurant {
    @Id
    @GeneratedValue(generator = "SEK_RESTAURANTS")
    @Column(name = "restaurant_id")
    private Long id;

    @NonNull
    @Column(unique = true)
    private String name;

    @NonNull
    private BigDecimal x;

    @NonNull
    private BigDecimal y;

    @Transient
    @Column(insertable = false)
    private BigDecimal distance;

    @Transient
    @Column(name = "IS_CORRECT_FOOD_TYPE",insertable = false)
    private Boolean isCorrectFoodType;

    @NonNull
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "restaurant_id", foreignKey = @ForeignKey(name = "fk_food_typ_restaurant_id"))
    private Set<FoodType> foodTypes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Restaurant that = (Restaurant) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
