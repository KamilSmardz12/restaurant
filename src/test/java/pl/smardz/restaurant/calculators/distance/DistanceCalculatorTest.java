package pl.smardz.restaurant.calculators.distance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.smardz.restaurant.dto.Distance;
import pl.smardz.restaurant.dto.Point;
import pl.smardz.restaurant.enums.Unit;
import pl.smardz.restaurant.services.DistanceCalculatorService;

import java.math.BigDecimal;

public class DistanceCalculatorTest {
    private final DistanceCalculatorService distanceCalculator =  new DistanceCalculatorService();
    @Test
    void calculateDistanceInKilometersTest() {
        final Point domostawa = Point.builder()
                .x(BigDecimal.valueOf(50.604918))
                .y(BigDecimal.valueOf(22.278260))
                .build();
        final Point sandomierz = Point.builder()
                .x(BigDecimal.valueOf(50.684854))
                .y(BigDecimal.valueOf(21.737200))
                .build();

        final Distance calculatedDistance = distanceCalculator.calculate(domostawa, sandomierz, Unit.KILOMETERS);
        final Distance expectedDistance = Distance.builder()
                .unit(Unit.KILOMETERS)
                .distance(BigDecimal.valueOf(39.1707))
                .build();

        Assertions.assertEquals(expectedDistance, calculatedDistance);
    }

    @Test
    void calculateDistanceInMilesTest() {
        final Point domostawa = Point.builder()
                .x(BigDecimal.valueOf(50.604918))
                .y(BigDecimal.valueOf(22.278260))
                .build();
        final Point sandomierz = Point.builder()
                .x(BigDecimal.valueOf(50.684854))
                .y(BigDecimal.valueOf(21.737200))
                .build();

        final Distance calculatedDistance = distanceCalculator.calculate(domostawa, sandomierz, Unit.MILES);
        final Distance expectedDistance = Distance.builder()
                .unit(Unit.MILES)
                .distance(BigDecimal.valueOf(24.3395))
                .build();

        Assertions.assertEquals(expectedDistance, calculatedDistance);
    }

    @Test
    void calculateDistanceInNauticalTest() {
        final Point domostawa = Point.builder()
                .x(BigDecimal.valueOf(50.604918))
                .y(BigDecimal.valueOf(22.278260))
                .build();
        final Point sandomierz = Point.builder()
                .x(BigDecimal.valueOf(50.684854))
                .y(BigDecimal.valueOf(21.737200))
                .build();

        final Distance calculatedDistance = distanceCalculator.calculate(domostawa, sandomierz, Unit.NAUTICAL);
        final Distance expectedDistance = Distance.builder()
                .unit(Unit.NAUTICAL)
                .distance(BigDecimal.valueOf(21.1365))
                .build();

        Assertions.assertEquals(expectedDistance, calculatedDistance);
    }

}
