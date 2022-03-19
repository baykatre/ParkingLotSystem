package co.anilozturk.ParkingLotSystem.system.ticket;

import co.anilozturk.ParkingLotSystem.system.spot.ParkingSpot;
import co.anilozturk.ParkingLotSystem.system.vehicle.Vehicle;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TICKET")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "VEHICLE_ID")
    private Vehicle vehicle;

    @ManyToOne
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "SPOT_ID")
    private ParkingSpot parkingSpot;

    private LocalDateTime entryTime;

    private LocalDateTime exitTime;
}
