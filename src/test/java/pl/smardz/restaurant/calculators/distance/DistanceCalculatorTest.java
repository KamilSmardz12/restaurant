package pl.smardz.restaurant.calculators.distance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.smardz.restaurant.dto.Distance;
import pl.smardz.restaurant.dto.Point;
import pl.smardz.restaurant.enums.Unit;
import pl.smardz.restaurant.services.DistanceCalculatorService;

import java.math.BigDecimal;

@SpringBootTest
public class DistanceCalculatorTest {
    private static final Point DOMOSTAWA = Point.builder()
            .x(BigDecimal.valueOf(50.604918))
            .y(BigDecimal.valueOf(22.278260))
            .build();
    private static final Point SANDOMIERZ = Point.builder()
            .x(BigDecimal.valueOf(50.684854))
            .y(BigDecimal.valueOf(21.737200))
            .build();
    @Autowired
    private DistanceCalculatorService distanceCalculator;

    @Test
    void calculateDistanceInKilometersTest() {
        final Distance calculatedDistance = distanceCalculator.calculate(DOMOSTAWA, SANDOMIERZ, Unit.KILOMETERS);
        final Distance expectedDistance = Distance.builder()
                .unit(Unit.KILOMETERS)
                .distance(BigDecimal.valueOf(39.1707))
                .build();

        Assertions.assertEquals(expectedDistance, calculatedDistance);
    }

    @Test
    void calculateDistanceInMilesTest() {
        final Distance calculatedDistance = distanceCalculator.calculate(DOMOSTAWA, SANDOMIERZ, Unit.MILES);
        final Distance expectedDistance = Distance.builder()
                .unit(Unit.MILES)
                .distance(BigDecimal.valueOf(24.3395))
                .build();

        Assertions.assertEquals(expectedDistance, calculatedDistance);
    }

    @Test
    void calculateDistanceInNauticalTest() {
        final Distance calculatedDistance = distanceCalculator.calculate(DOMOSTAWA, SANDOMIERZ, Unit.NAUTICAL);
        final Distance expectedDistance = Distance.builder()
                .unit(Unit.NAUTICAL)
                .distance(BigDecimal.valueOf(21.1365))
                .build();

        Assertions.assertEquals(expectedDistance, calculatedDistance);
    }

}
