package pl.smardz.restaurant.calculators.distance.pl.smardz.restaurant.validators;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.smardz.restaurant.exceptions.IncompleteDataRequestException;
import pl.smardz.restaurant.validators.CoordinatesValidator;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CoordinatesValidatorTest {

    @Autowired
    private CoordinatesValidator coordinatesValidator;

    @Test
    void correctValueOfTheXCoordinationVariableTest() {
        assertDoesNotThrow(() -> coordinatesValidator.validateTheXCoordinateValue(BigDecimal.valueOf(0)));
    }

    @Test
    void invalidValueOfTheXCoordinationVariableTest() {
        IncompleteDataRequestException exception = assertThrows(
                IncompleteDataRequestException.class,
                () -> coordinatesValidator.validateTheXCoordinateValue(null)
        );

        assertEquals("Invalid value for X", exception.getMessage());
    }

    @Test
    void correctValueOfTheYCoordinationVariableTest() {
        assertDoesNotThrow(() -> coordinatesValidator.validateTheYCoordinateValue(BigDecimal.valueOf(0)));
    }

    @Test
    void invalidValueOfTheYCoordinationVariableTest() {
        IncompleteDataRequestException exception = assertThrows(
                IncompleteDataRequestException.class,
                () -> coordinatesValidator.validateTheYCoordinateValue(null)
        );

        assertEquals("Invalid value for Y", exception.getMessage());
    }
}
