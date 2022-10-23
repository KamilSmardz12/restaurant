package pl.smardz.restaurant.exceptions;

public class RequestDataIsIncomplete extends  IllegalArgumentException{
    public RequestDataIsIncomplete(String message) {
        super(message);
    }
}
