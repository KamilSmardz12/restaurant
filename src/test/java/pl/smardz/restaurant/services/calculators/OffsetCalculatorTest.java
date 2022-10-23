package pl.smardz.restaurant.services.calculators;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.smardz.restaurant.payload.request.RestaurantRequest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OffsetCalculatorTest {

    @Autowired
    private OffsetCalculator offsetCalculator;


    @ParameterizedTest
    @MethodSource("providePagePageSizeAndResult")
    void calculateOffsetTest(Integer page, Integer pageSize, Integer expectedResult) {
        final RestaurantRequest restaurantRequest = RestaurantRequest.builder()
                .page(page)
                .build();

        assertEquals(expectedResult, offsetCalculator.calculateOffset(restaurantRequest, pageSize));
    }

    private static Stream<Arguments> providePagePageSizeAndResult() {
        return Stream.of(
                Arguments.of(null, 5, 0),
                Arguments.of(0, 5, 0),
                Arguments.of(1, 5, 5),
                Arguments.of(5, 5, 25)
        );
    }
}
