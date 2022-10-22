package pl.smardz.restaurant.calculators.distance;

import pl.smardz.restaurant.dto.Distance;
import pl.smardz.restaurant.dto.Point;
import pl.smardz.restaurant.enums.Unit;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DistanceCalculator {
    private static final int ACCURACY_OF_CALCULATIONS = 4;

    public static Distance calculate(Point restaurantPoint, Point requestPoint, Unit unit) {
        if (isTheSamePoint(restaurantPoint, requestPoint)) {
            return Distance.builder()
                    .distance(BigDecimal.ZERO)
                    .unit(unit)
                    .build();
        }

        return Distance.builder()
                .distance(calculateDistance(restaurantPoint, requestPoint, unit))
                .unit(unit)
                .build();
    }

    private static BigDecimal calculateDistance(Point restaurantPoint, Point requestPoint, Unit unit) {
        final BigDecimal theta = calculateTheta(restaurantPoint, requestPoint);

        double distance = Math.sin(Math.toRadians(restaurantPoint.getX().doubleValue())) * Math.sin(Math.toRadians(requestPoint.getX().doubleValue()))
                + Math.cos(Math.toRadians(restaurantPoint.getX().doubleValue())) * Math.cos(Math.toRadians(requestPoint.getX().doubleValue())) * Math.cos(Math.toRadians(theta.doubleValue()));

        distance = Math.acos(distance);
        distance = Math.toDegrees(distance);
        distance = distance * 60 * 1.1515;

        return BigDecimal
                .valueOf(distance)
                .multiply(unit.getMultiplier())
                .setScale(ACCURACY_OF_CALCULATIONS, RoundingMode.HALF_UP);
    }

    private static BigDecimal calculateTheta(Point restaurantPoint, Point requestPoint) {
        return restaurantPoint.getY().subtract(requestPoint.getY());
    }

    private static boolean isTheSamePoint(Point restaurantPoint, Point requestPoint) {
        return (restaurantPoint.getX().equals(requestPoint.getX())) && (restaurantPoint.getY().equals(requestPoint.getY()));
    }
}
