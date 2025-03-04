package by.sakuuj.agsr.dto.response;


import by.sakuuj.agsr.constants.DateTimeConstants;
import by.sakuuj.agsr.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record PatientResponse(
        UUID id,

        @Schema(example = "Иванов Иван Иванович")
        String name,

        @Schema(enumAsRef = true)
        Gender gender,

        @Schema(example = "2025-03-02T19:53:58")
        @JsonFormat(pattern = DateTimeConstants.DEFAULT_FORMAT, timezone = DateTimeConstants.DEFAULT_TIMEZONE)
        Instant birthDate
) {
}
