package co.anilozturk.ParkingLotSystem.system.model;

import co.anilozturk.ParkingLotSystem.system.ticket.Ticket;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(value = { "tickets" })
public class ParkingOutput {

    private String message;

    private List<Ticket> tickets;
}
