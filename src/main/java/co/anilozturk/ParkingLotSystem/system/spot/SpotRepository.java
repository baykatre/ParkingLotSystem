package co.anilozturk.ParkingLotSystem.system.spot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SpotRepository extends JpaRepository<ParkingSpot, Integer> {

    List<ParkingSpot> findAllByState(boolean state);

    @Transactional
    @Modifying
    @Query("update ParkingSpot set state = :state where id = :parkingSpotId")
    void updateState(@Param("parkingSpotId") Integer parkingSpotId, @Param("state") boolean state);
}
