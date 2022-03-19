package co.anilozturk.ParkingLotSystem.system.vehicle;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
@JsonSerialize(using = VehicleTypeSerializer.class)
@JsonDeserialize(using = VehicleTypeDeserializer.class)
public enum VehicleType implements Serializable {

    CAR("C", 1), JEEP("J", 2), TRUCK("T", 4);

    private final String value;

    private final Integer size;
}
