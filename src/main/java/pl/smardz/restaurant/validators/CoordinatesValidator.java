package pl.smardz.restaurant.validators;

import org.springframework.stereotype.Service;
import pl.smardz.restaurant.exceptions.IncompleteDataRequestException;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CoordinatesValidator {
    private static final String INVALID_VALUE_FOR = "Invalid value for ";
    private static final String X = "X";
    private static final String Y = "Y";

    public void validateTheXCoordinateValue(BigDecimal coordinateValue) {
        validateTheCoordinateValue(coordinateValue, X);
    }

    public void validateTheYCoordinateValue(BigDecimal coordinateValue) {
        validateTheCoordinateValue(coordinateValue, Y);
    }

    private void validateTheCoordinateValue(BigDecimal coordinateValue, String coordinateName) {
        Optional.ofNullable(coordinateValue)
                .orElseThrow(() -> new IncompleteDataRequestException(INVALID_VALUE_FOR + coordinateName));
    }
}
