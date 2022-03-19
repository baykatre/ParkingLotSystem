package co.anilozturk.ParkingLotSystem.exception;

import co.anilozturk.ParkingLotSystem.generic.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

@ControllerAdvice
public class ParkingExceptionHandler {

  @ExceptionHandler(ParkingGenericException.class)
  ResponseEntity<?> handle(ParkingGenericException e) {
    final String errorMessage = String.join("-", e.getMessages().orElse(new String[] {""}));

    return getInternalServerErrorResponse(errorMessage);
  }

  private ResponseEntity<?> getInternalServerErrorResponse(String errorMessage) {

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(Response.from(Collections.singletonMap("message", errorMessage)));
  }
}
