package by.sakuuj.agsr.mapper.json;

import by.sakuuj.agsr.enums.Gender;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class GenderDeserializer extends StdDeserializer<Gender> {

    protected GenderDeserializer() {
        super(Gender.class);
    }

    @Override
    public Gender deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JacksonException {

        String genderValue = jsonParser.getValueAsString();

        if (genderValue == null) {
            return null;
        }

        return Gender.valueOf(genderValue.toUpperCase());
    }
}
