package co.anilozturk.ParkingLotSystem.system.model;

import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
public class LeaveInput {

    @Size(max = 10)
    private String plate;
}
