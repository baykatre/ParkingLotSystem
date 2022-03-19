package co.anilozturk.ParkingLotSystem.system.vehicle;

import lombok.*;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import static co.anilozturk.ParkingLotSystem.system.constant.SystemConstants.TURKEY_PLATE_VALIDATION;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "VEHICLE")
public class Vehicle {

  @Id
  @Pattern(regexp = TURKEY_PLATE_VALIDATION)//TODO: fix there
  private String plate;

  @Convert(converter = VehicleTypeConverter.class)
  private VehicleType type;

  private String color;
}
