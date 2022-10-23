package pl.smardz.restaurant.model;

import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.math.BigDecimal;
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
//    @Transient
    private BigDecimal distance;

    @NonNull
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "restaurant_id", foreignKey = @ForeignKey(name = "fk_food_typ_restaurant_id"))
    private Set<FoodType> foodTypes;
}
