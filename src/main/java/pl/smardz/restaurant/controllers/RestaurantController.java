package pl.smardz.restaurant.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.smardz.restaurant.enums.Unit;
import pl.smardz.restaurant.model.Restaurant;
import pl.smardz.restaurant.payload.request.RestaurantRequestDataToFindRestaurant;
import pl.smardz.restaurant.payload.request.RestaurantRequestDataToSave;
import pl.smardz.restaurant.payload.response.FindedRestaurantData;
import pl.smardz.restaurant.services.RestaurantService;

import java.math.BigDecimal;
import java.util.Set;

@RequiredArgsConstructor
@RestController
public class RestaurantController {
    private final RestaurantService service;

    @PutMapping("/save")
    public ResponseEntity<Restaurant> saveRestaurant(
            @Validated
            @RequestBody
            RestaurantRequestDataToSave requestData) {

        return service.save(requestData)//
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/findAll")
    public ResponseEntity<Set<FindedRestaurantData>> findAllRestaurants(
            @RequestParam BigDecimal x,
            @RequestParam BigDecimal y,
            @RequestParam Unit unit,
            @RequestParam String foodType,
            @RequestParam int pageNr
    ) {

        RestaurantRequestDataToFindRestaurant restaurantRequestDataToFindRestaurant = RestaurantRequestDataToFindRestaurant.builder()
                .x(x)
                .y(y)
                .unit(unit)
                .foodType(foodType)
                .pageNr(pageNr)
                .build();

        return ResponseEntity.ok(service.findAll(restaurantRequestDataToFindRestaurant));
    }

    @PostMapping("/findAll")
    public ResponseEntity<Set<FindedRestaurantData>> findAllRestaurants(
            @Validated
            @RequestBody
            RestaurantRequestDataToFindRestaurant restaurantRequestData,
            @RequestParam(required = false) int pageNr
    ) {
        return ResponseEntity.ok(service.findAll(
                RestaurantRequestDataToFindRestaurant.builder()
                        .pageNr(calculatePageNr(pageNr))
                        .x(restaurantRequestData.getX())
                        .y(restaurantRequestData.getY())
                        .unit(restaurantRequestData.getUnit())
                        .foodType(restaurantRequestData.getFoodType())
                        .build())
        );
    }

    private int calculatePageNr(int pageNr) {
        return Math.max(pageNr, 0);
    }

}
