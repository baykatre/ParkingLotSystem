package co.anilozturk.ParkingLotSystem.util;

import co.anilozturk.ParkingLotSystem.system.spot.ParkingSpot;

import java.util.List;

public final class ParkingUtils {

  public static void populateSuitableParkingSpot(
      List<ParkingSpot> parkingSpotList, Integer vehicleSize) {
    if ((1 == vehicleSize) || vehicleSize > parkingSpotList.size()) {
      return;
    }
    while (!ParkingUtils.isSequentialSpotsUntilVehicleSize(parkingSpotList, vehicleSize)) {
      parkingSpotList.remove(0);
      if (vehicleSize > parkingSpotList.size()) {
        break;
      }
    }
  }

  public static boolean isSequentialSpotsUntilVehicleSize(
      List<ParkingSpot> parkingSpotList, Integer vehicleSize) {
    for (int i = 0; i < vehicleSize - 1; i++) {
      if (!isSequentialSpots(parkingSpotList.get(i), parkingSpotList.get(i + 1))) {
        return false;
      }
    }
    return true;
  }

  private static boolean isSequentialSpots(ParkingSpot p1, ParkingSpot p2) {
    return p1.getId() + 1 == p2.getId();
  }
}
