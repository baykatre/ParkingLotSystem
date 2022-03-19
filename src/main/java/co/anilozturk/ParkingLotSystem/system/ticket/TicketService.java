package co.anilozturk.ParkingLotSystem.system.ticket;

import co.anilozturk.ParkingLotSystem.system.spot.ParkingSpot;
import co.anilozturk.ParkingLotSystem.system.vehicle.Vehicle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {

  private final TicketRepository repository;

  public List<Ticket> save(Vehicle vehicle, List<ParkingSpot> parkingSpot) {

    final LocalDateTime now = LocalDateTime.now();

    final List<Ticket> tickets =
        parkingSpot.stream()
            .map(spot -> Ticket.builder().vehicle(vehicle).parkingSpot(spot).entryTime(now).build())
            .collect(Collectors.toList());

    return repository.saveAll(tickets);
  }

  public List<Ticket> leave(Vehicle vehicle) {

    final List<Ticket> tickets = getVehicleActiveTickets(vehicle);

    final LocalDateTime now = LocalDateTime.now();

    tickets.forEach(ticket -> ticket.setExitTime(now));

    return repository.saveAll(tickets);
  }

  public List<Ticket> getVehicleActiveTickets(Vehicle vehicle) {

    return repository.findAllByVehicleAndExitTimeIsNull(vehicle);
  }

  public List<Ticket> getAllActiveTickets() {

    return repository.findAllByExitTimeIsNull();
  }
}
