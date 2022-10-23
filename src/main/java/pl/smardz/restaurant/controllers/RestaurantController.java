package pl.smardz.restaurant.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.smardz.restaurant.payload.request.RestaurantRequest;
import pl.smardz.restaurant.payload.request.RestaurantSaveRequest;
import pl.smardz.restaurant.payload.response.RestaurantData;
import pl.smardz.restaurant.services.restaurant.RestaurantService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RestaurantController {
    private final RestaurantService service;

    @PutMapping("/restaurant")
    public ResponseEntity<RestaurantData> saveRestaurant(
            @Validated
            @RequestBody
            RestaurantSaveRequest requestData) {

        return service.save(requestData)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.badRequest()::build);
    }

    @PostMapping("/restaurants")
    public ResponseEntity<List<RestaurantData>> findRestaurants(
            @RequestBody
            RestaurantRequest requestData,
            @RequestParam(required = false) Integer page
    ) {
        return ResponseEntity.ok(service.findRestaurants(
                RestaurantRequest.builder()
                        .page(page)
                        .x(requestData.getX())
                        .y(requestData.getY())
                        .unit(requestData.getUnit())
                        .foodType(requestData.getFoodType())
                        .build())
        );
    }


}
