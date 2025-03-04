package by.sakuuj.agsr.enums;

import by.sakuuj.agsr.mapper.json.GenderDeserializer;
import by.sakuuj.agsr.mapper.json.GenderSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "gender",
        allowableValues = {"male", "female"},
        example = "male"
)
@JsonSerialize(using = GenderSerializer.class)
@JsonDeserialize(using = GenderDeserializer.class)
public enum Gender {
    @Hidden
    MALE,
    @Hidden
    FEMALE,
}
