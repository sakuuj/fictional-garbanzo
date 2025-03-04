package by.sakuuj.agsr.mapper.json;

import by.sakuuj.agsr.enums.Gender;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class GenderSerializer extends StdSerializer<Gender> {

    protected GenderSerializer() {
        super(Gender.class);
    }

    @Override
    public void serialize(Gender value, JsonGenerator gen, SerializerProvider provider) throws IOException {

        String valueToWrite = value == null
                ? null
                : value.toString().toLowerCase();

        gen.writeString(valueToWrite);
    }
}
