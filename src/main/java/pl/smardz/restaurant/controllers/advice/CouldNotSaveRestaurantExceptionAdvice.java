package pl.smardz.restaurant.controllers.advice;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class CouldNotSaveRestaurantExceptionAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> couldNotSaveRestaurantException(Throwable ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>("Could not save restaurant. Restaurant name is unique",HttpStatus.BAD_REQUEST);
    }

}
