package co.anilozturk.ParkingLotSystem.system;

import co.anilozturk.ParkingLotSystem.config.ParkingMessageSource;
import co.anilozturk.ParkingLotSystem.exception.ParkingGenericException;
import co.anilozturk.ParkingLotSystem.generic.CheckConditions;
import co.anilozturk.ParkingLotSystem.system.model.LeaveInput;
import co.anilozturk.ParkingLotSystem.system.model.ParkInput;
import co.anilozturk.ParkingLotSystem.system.model.ParkingOutput;
import co.anilozturk.ParkingLotSystem.system.spot.ParkingSpot;
import co.anilozturk.ParkingLotSystem.system.spot.SpotService;
import co.anilozturk.ParkingLotSystem.system.ticket.Ticket;
import co.anilozturk.ParkingLotSystem.system.ticket.TicketService;
import co.anilozturk.ParkingLotSystem.system.vehicle.Vehicle;
import co.anilozturk.ParkingLotSystem.system.vehicle.VehicleService;
import co.anilozturk.ParkingLotSystem.system.vehicle.VehicleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static co.anilozturk.ParkingLotSystem.util.ParkingUtils.populateSuitableParkingSpot;

@Service
@RequiredArgsConstructor
public class SystemService {

  public static final String SUCCESS_PARK = "message.success_park";

  public static final String SUCCESS_LEAVE = "message.success_leave";

  private final SpotService spotService;

  private final TicketService ticketService;

  private final VehicleService vehicleService;

  private final ParkingMessageSource messageSource;

  public ParkingOutput status() {

    final List<Ticket> allActiveTickets = ticketService.getAllActiveTickets();
    final Map<Vehicle, List<Ticket>> carTicketList =
        allActiveTickets.stream().collect(Collectors.groupingBy(Ticket::getVehicle));

    return ParkingOutput.builder()
        .message(getStatusMessage(carTicketList))
        .tickets(allActiveTickets)
        .build();
  }

  @CheckConditions
  ParkingOutput park(ParkInput input) {
    final Vehicle vehicle = input.getVehicle();
    final VehicleType vehicleType = vehicle.getType();
    final List<ParkingSpot> parkingSpotList = spotService.getAvailableSpots();

    final Integer vehicleSize = vehicleType.getSize();

    populateSuitableParkingSpot(parkingSpotList, vehicleSize);

    final List<ParkingSpot> reservedParkingSpot = parkingSpotList.subList(0, vehicleSize);

    final List<Ticket> tickets = getTicket(vehicle, reservedParkingSpot);

    return ParkingOutput.builder()
        .tickets(tickets)
        .message(getOutputMessage(tickets, SUCCESS_PARK))
        .build();
  }

  @CheckConditions
  ParkingOutput leave(LeaveInput input) {

    final String plate = input.getPlate();

    final Vehicle vehicleFromDb = vehicleService.getVehicle(plate);

    final List<Ticket> leftTicketList = ticketService.leave(vehicleFromDb);

    final List<ParkingSpot> leftParkingSpot =
        leftTicketList.stream().map(Ticket::getParkingSpot).collect(Collectors.toList());
    spotService.changeState(leftParkingSpot);

    return ParkingOutput.builder()
        .tickets(leftTicketList)
        .message(getOutputMessage(leftTicketList, SUCCESS_LEAVE))
        .build();
  }

  private String getOutputMessage(List<Ticket> tickets, String messageType) {

    final Vehicle vehicle =
        tickets.stream()
            .map(Ticket::getVehicle)
            .findFirst()
            .orElseThrow(() -> new ParkingGenericException(messageSource));
    final String spotNames =
        tickets.stream()
            .map(Ticket::getParkingSpot)
            .map(ParkingSpot::getId)
            .map(String::valueOf)
            .collect(Collectors.joining("-"));

    return messageSource.getMessage(messageType, new String[] {vehicle.getPlate(), spotNames});
  }

  @Transactional
  List<Ticket> getTicket(Vehicle vehicle, List<ParkingSpot> parkingSpot) {

    final List<Ticket> tickets = ticketService.save(vehicle, parkingSpot);
    spotService.changeState(parkingSpot);

    return tickets;
  }


  private String getStatusMessage(Map<Vehicle, List<Ticket>> carTicketList) {
    final StringBuilder carSpotList = new StringBuilder();
    carTicketList.forEach(
            ((vehicle, tickets) -> {
              carSpotList
                      .append(vehicle.getPlate())
                      .append(" (")
                      .append(vehicle.getColor())
                      .append(") ")
                      .append("Slots: [")
                      .append(
                              tickets.stream()
                                      .map(ticket -> ticket.getParkingSpot().getId().toString())
                                      .collect(Collectors.joining("-")))
                      .append("], ");
            }));
    return carSpotList.toString();
  }
}
