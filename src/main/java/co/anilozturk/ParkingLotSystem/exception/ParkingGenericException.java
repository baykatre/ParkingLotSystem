package co.anilozturk.ParkingLotSystem.exception;

import co.anilozturk.ParkingLotSystem.config.ParkingMessageSource;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;

@Getter
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ParkingGenericException extends RuntimeException {

  private static final String UNKNOWN_ERROR = "error.parking_spot.unknown";

  private final String code;

  private final String[] messages;

  ParkingGenericException(Exception exception) {
    super(exception.getMessage(), exception);
    this.code = null;
    this.messages = null;
  }

  public ParkingGenericException() {

    this.code = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());
    this.messages = new String[] {UNKNOWN_ERROR};
  }

  public ParkingGenericException(String code, String... messages) {

    this.code = code;
    this.messages = messages;
  }

  public ParkingGenericException(String... messages) {

    this.code = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());
    this.messages = messages;
  }

  public ParkingGenericException(String messages) {

    this.code = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());
    this.messages = new String[] {messages};
  }

  public ParkingGenericException(ParkingMessageSource messageSource) {

    this.code = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());
    this.messages = new String[] {messageSource.getMessage(UNKNOWN_ERROR)};
  }

  public ParkingGenericException(ParkingMessageSource messageSource, String message) {

    this.code = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());
    this.messages = new String[] {messageSource.getMessage(message)};
  }

  public ParkingGenericException(ParkingMessageSource messageSource, String message, String[] parameters) {

    this.code = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());
    this.messages = new String[] {messageSource.getMessage(message, parameters)};
  }

  public Optional<String> getErrorCode() {

    return Optional.ofNullable(this.code);
  }

  public Optional<String[]> getMessages() {

    return Optional.ofNullable(this.messages);
  }
}
