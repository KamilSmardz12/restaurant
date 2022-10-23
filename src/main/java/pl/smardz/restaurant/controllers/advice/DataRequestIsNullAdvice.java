package pl.smardz.restaurant.controllers.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.smardz.restaurant.exceptions.RequestDataIsIncomplete;

@ControllerAdvice
@Slf4j
public class DataRequestIsNullAdvice {
    @ExceptionHandler(RequestDataIsIncomplete.class)
    public ResponseEntity<?> couldNotSaveRestaurantException(Throwable ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
