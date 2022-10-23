package pl.smardz.restaurant.controllers.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.smardz.restaurant.exceptions.RequestDataIsIncomplete;

@ControllerAdvice
public class DataRequestIsNullAdvice {

    @ExceptionHandler(RequestDataIsIncomplete.class)
    public ResponseEntity<?> couldNotSaveRestaurantException(Throwable ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
