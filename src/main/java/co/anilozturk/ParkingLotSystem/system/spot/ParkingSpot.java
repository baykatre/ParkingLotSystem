package co.anilozturk.ParkingLotSystem.system.spot;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PARKING_SPOT")
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private int size;

    private boolean state;
}
