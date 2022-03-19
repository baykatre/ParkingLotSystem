package co.anilozturk.ParkingLotSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class ParkingLotSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingLotSystemApplication.class, args);
	}

}
