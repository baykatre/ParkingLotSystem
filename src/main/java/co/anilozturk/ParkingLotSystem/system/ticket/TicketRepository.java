package co.anilozturk.ParkingLotSystem.system.ticket;

import co.anilozturk.ParkingLotSystem.system.vehicle.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, String> {

    List<Ticket> findAllByVehicleAndExitTimeIsNull(Vehicle vehicle);

    List<Ticket> findAllByExitTimeIsNull();

}
