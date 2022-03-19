package co.anilozturk.ParkingLotSystem.system.vehicle;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleService {

  private final VehicleRepository repository;

  public Vehicle getVehicle(String plate) {
    return repository.findById(plate).orElse(null);
  }
}
