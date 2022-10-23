package pl.smardz.restaurant.controllers.advice;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CouldNotSaveRestaurantExceptionAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> couldNotSaveRestaurantException(Throwable ex) {
        return new ResponseEntity<>("Could not save restaurant. Restaurant name is unique",HttpStatus.BAD_REQUEST);
    }

}
