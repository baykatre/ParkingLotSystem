package co.anilozturk.ParkingLotSystem.system.vehicle;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class VehicleTypeSerializer extends StdSerializer<VehicleType> {
    public VehicleTypeSerializer(Class<VehicleType> t) {
        super(t);
    }

    public VehicleTypeSerializer() {
        this(null);
    }

    @Override
    public void serialize(VehicleType vehicleType, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", vehicleType.name());
        jsonGenerator.writeStringField("code", vehicleType.getValue());
        jsonGenerator.writeEndObject();
    }
}
