package pl.smardz.restaurant.exceptions;

public class IncompleteDataRequestException extends IllegalArgumentException {
    public IncompleteDataRequestException(String message) {
        super(message);
    }
}
