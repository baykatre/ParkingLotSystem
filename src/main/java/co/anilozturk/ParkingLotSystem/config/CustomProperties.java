package co.anilozturk.ParkingLotSystem.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "co.anilozturk")
public class CustomProperties {

    private String language;

    private String region;
}
