package co.anilozturk.ParkingLotSystem.system.vehicle;

import co.anilozturk.ParkingLotSystem.config.ParkingMessageSource;
import co.anilozturk.ParkingLotSystem.exception.ParkingGenericException;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ObjectUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.Objects;

@RequiredArgsConstructor
@Converter(autoApply = true)
public class VehicleTypeConverter implements AttributeConverter<VehicleType, String> {

  private final ParkingMessageSource messageSource;

  @Override
  public String convertToDatabaseColumn(VehicleType vehicleType) {

    if (Objects.nonNull(vehicleType)) {
      return vehicleType.getValue();
    }
    return null;
  }

  @Override
  public VehicleType convertToEntityAttribute(String value) {

    if (ObjectUtils.isEmpty(value)) {
      return null;
    }

    return Arrays.stream(VehicleType.values())
        .filter(type -> type.getValue().equals(value))
        .findFirst()
        .orElseThrow(() -> new ParkingGenericException(messageSource));
  }
}
