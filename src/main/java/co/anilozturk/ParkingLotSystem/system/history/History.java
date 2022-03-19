package co.anilozturk.ParkingLotSystem.system.history;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "HISTORY")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "TICKET_ID_LIST")
    private String tickets;

    private Long executionTime;

    private String plate;

    private String operationType;
}