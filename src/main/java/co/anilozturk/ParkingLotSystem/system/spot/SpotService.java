package co.anilozturk.ParkingLotSystem.system.spot;

import co.anilozturk.ParkingLotSystem.system.vehicle.Vehicle;
import co.anilozturk.ParkingLotSystem.system.vehicle.VehicleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpotService {

    private final SpotRepository repository;

    public List<ParkingSpot> getAvailableSpots(){

        return repository.findAllByState(false);
    }

    public List<ParkingSpot> changeState(List<ParkingSpot> parkingSpot){

    return parkingSpot.stream()
        .map(
            spot -> {
              final Integer id = spot.getId();
              final boolean state = spot.isState();
              repository.updateState(id, !state);
              return repository.findById(id).orElse(null);
            })
        .collect(Collectors.toList());
    }
}
