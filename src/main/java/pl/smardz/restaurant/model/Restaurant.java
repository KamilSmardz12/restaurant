package pl.smardz.restaurant.model;

import lombok.Builder;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Builder
@Entity(name = "RESTAURANTS")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long id;
    @NonNull
    @Column(unique = true)
    private String name;
    @NonNull
    private BigDecimal x;
    @NonNull
    private BigDecimal y;
    @NonNull
    @OneToMany( cascade = CascadeType.ALL)
//    @JoinColumn(name = "restaurant_id")
    @JoinColumn(name="restaurant_id", referencedColumnName="restaurant_id", foreignKey=@ForeignKey(name = "fk_food_typ_restaurant_id"))

    private Set<FoodType> foodTypes;

    public Restaurant() {
    }

    public Restaurant(Long id, String name, BigDecimal x, BigDecimal y, Set<FoodType> foodTypes) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.foodTypes = foodTypes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getX() {
        return x;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public Set<FoodType> getFoodTypes() {
        return foodTypes;
    }

    public void setFoodTypes(Set<FoodType> foodTypes) {
        this.foodTypes = foodTypes;
    }
}
