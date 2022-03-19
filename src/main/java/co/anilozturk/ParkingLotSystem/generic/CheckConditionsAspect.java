package co.anilozturk.ParkingLotSystem.generic;

import co.anilozturk.ParkingLotSystem.config.ParkingMessageSource;
import co.anilozturk.ParkingLotSystem.exception.ParkingGenericException;
import co.anilozturk.ParkingLotSystem.system.model.LeaveInput;
import co.anilozturk.ParkingLotSystem.system.model.ParkInput;
import co.anilozturk.ParkingLotSystem.system.spot.ParkingSpot;
import co.anilozturk.ParkingLotSystem.system.spot.SpotService;
import co.anilozturk.ParkingLotSystem.system.ticket.Ticket;
import co.anilozturk.ParkingLotSystem.system.ticket.TicketService;
import co.anilozturk.ParkingLotSystem.system.vehicle.Vehicle;
import co.anilozturk.ParkingLotSystem.system.vehicle.VehicleService;
import co.anilozturk.ParkingLotSystem.system.vehicle.VehicleType;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static co.anilozturk.ParkingLotSystem.util.ParkingUtils.populateSuitableParkingSpot;

@Aspect
@Component
@RequiredArgsConstructor
public class CheckConditionsAspect {

  public static final String VEHICLE_IS_ACTIVE_ON_TIME = "error.parking.vehicle_is_active";

  private static final String FREE_PARKING_SPOT_NOT_FOUND_ERROR = "error.parking_area.full";

  private static final String VEHICLE_IS_NOT_FOUND = "error.leave.vehicle_is_not_found";

  private static final String TICKET_IS_NOT_FOUND = "error.leave.ticket_is_not_found";

  private static final String PARAMETER_NOT_RECOGNIZED = "error.generic.unrecognized_parameter";

  private final VehicleService vehicleService;

  private final TicketService ticketService;

  private final SpotService spotService;

  private final ParkingMessageSource messageSource;

  @Before("@annotation(CheckConditions)")
  public void skipValidation(JoinPoint joinPoint) {

    check(joinPoint);
  }

  private void check(JoinPoint joinPoint) {

    final Object parameter = Arrays.stream(joinPoint.getArgs()).findFirst().orElse(null);
    if (Objects.isNull(parameter)) {
      return;
    }

    final MethodSignature signature = (MethodSignature) joinPoint.getSignature();

    final Class<?> clazz =
        Arrays.stream(signature.getMethod().getParameterTypes()).findFirst().orElse(null);

    if (ParkInput.class.equals(clazz)) {
      checkPark((ParkInput) parameter);
      return;
    }

    if (LeaveInput.class.equals(clazz)) {
      checkLeave((LeaveInput) parameter);
      return;
    }

    throw new ParkingGenericException(messageSource, PARAMETER_NOT_RECOGNIZED);
  }

  private void checkLeave(LeaveInput input) {

    final String plate = input.getPlate();

    final Vehicle vehicleFromDb = vehicleService.getVehicle(plate);

    if (Objects.isNull(vehicleFromDb)) {
      throw new ParkingGenericException(messageSource, VEHICLE_IS_NOT_FOUND);
    }

    final List<Ticket> activeTicketList = ticketService.getVehicleActiveTickets(vehicleFromDb);

    if (CollectionUtils.isEmpty(activeTicketList)) {
      throw new ParkingGenericException(messageSource, TICKET_IS_NOT_FOUND, new String[] {plate});
    }
  }

  private void checkPark(ParkInput input) {

    final Vehicle vehicle = input.getVehicle();

    final String plate = vehicle.getPlate();

    final Vehicle vehicleFromDb = vehicleService.getVehicle(plate);

    final List<Ticket> activeTicketList = ticketService.getVehicleActiveTickets(vehicleFromDb);

    if (!CollectionUtils.isEmpty(activeTicketList)) {
      throw new ParkingGenericException(messageSource, VEHICLE_IS_ACTIVE_ON_TIME);
    }

    final VehicleType vehicleType = vehicle.getType();
    final Integer vehicleSize = vehicleType.getSize();

    final List<ParkingSpot> parkingSpotList = spotService.getAvailableSpots();

    populateSuitableParkingSpot(parkingSpotList, vehicleType.getSize());

    if (vehicleSize > parkingSpotList.size()) {
      throw new ParkingGenericException(messageSource, FREE_PARKING_SPOT_NOT_FOUND_ERROR);
    }
  }
}
