package pl.smardz.restaurant.exceptions;

public class ExportErrorException extends RuntimeException {
    public ExportErrorException(String message) {
        super(message);
    }
}
