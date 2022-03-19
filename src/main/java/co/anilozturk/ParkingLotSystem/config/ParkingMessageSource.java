package co.anilozturk.ParkingLotSystem.config;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ParkingMessageSource {

  private final MessageSource messageSource;
  private final Locale defaultLocale;

  public ParkingMessageSource(MessageSource messageSource, CustomProperties properties) {
    this.messageSource = messageSource;

    defaultLocale =
        new Locale.Builder()
            .setLanguage(properties.getLanguage())
            .setRegion(properties.getRegion())
            .build();
  }

  public String getMessage(String code, String[] args, Locale locale) {
    return messageSource.getMessage(code, args, locale);
  }

  public String getMessage(String code, String[] args) {
    return messageSource.getMessage(code, args, this.defaultLocale);
  }

  public String getMessage(String code) {
    return messageSource.getMessage(code, null, this.defaultLocale);
  }
}
