package co.anilozturk.ParkingLotSystem.system.vehicle;

import co.anilozturk.ParkingLotSystem.exception.ParkingGenericException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Arrays;

public class VehicleTypeDeserializer extends StdDeserializer<VehicleType> {
    public VehicleTypeDeserializer(Class<VehicleType> t) {
        super(t);
    }

    VehicleTypeDeserializer() {
        super(VehicleType.class);
    }

    @Override
    public VehicleType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        final String value = jsonParser.getValueAsString();

        return Arrays.stream(VehicleType.values())
                .filter(vt -> value.equals(vt.getValue()))
                .findFirst().orElseThrow(() -> new ParkingGenericException("deneme"));
    }
}
