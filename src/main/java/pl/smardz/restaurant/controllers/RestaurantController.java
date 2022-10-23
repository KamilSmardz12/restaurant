package pl.smardz.restaurant.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.smardz.restaurant.enums.Unit;
import pl.smardz.restaurant.payload.request.RestaurantRequest;
import pl.smardz.restaurant.payload.request.RestaurantSaveRequest;
import pl.smardz.restaurant.payload.response.RestaurantData;
import pl.smardz.restaurant.services.restaurant.CsvRestaurantExportService;
import pl.smardz.restaurant.services.restaurant.ExcelRestaurantService;
import pl.smardz.restaurant.services.restaurant.RestaurantService;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class RestaurantController {
    private final RestaurantService service;
    private final CsvRestaurantExportService csvExportService;
    private final ExcelRestaurantService excelService;

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


    @GetMapping("/restaurants/csv")
    public void restaurantsToCsv(
            HttpServletResponse response,
            @RequestParam() String name,
            @RequestParam() BigDecimal x,
            @RequestParam() BigDecimal y,
            @RequestParam(required = false) Unit unit,
            @RequestParam(required = false) String foodType,
            @RequestParam(required = false) Integer page
    ) {
        final RestaurantRequest restaurantRequest = RestaurantRequest.builder()
                .page(page)
                .x(x)
                .y(y)
                .unit(unit)
                .foodType(foodType)
                .build();

        csvExportService.export(response, name, restaurantRequest);
    }

    @GetMapping("/restaurants/excel")
    public void restaurantsToExcel(
            HttpServletResponse response,
            @RequestParam() String name,
            @RequestParam() BigDecimal x,
            @RequestParam() BigDecimal y,
            @RequestParam(required = false) Unit unit,
            @RequestParam(required = false) String foodType,
            @RequestParam(required = false) Integer page
    ) {
        final RestaurantRequest restaurantRequest = RestaurantRequest.builder()
                .page(page)
                .x(x)
                .y(y)
                .unit(unit)
                .foodType(foodType)
                .build();

        excelService.export(response, restaurantRequest, name);
    }


}
