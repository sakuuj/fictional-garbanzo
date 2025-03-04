package by.sakuuj.agsr.dto.request;

import by.sakuuj.agsr.constants.DateTimeConstants;
import by.sakuuj.agsr.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.Instant;

@Builder
public record PatientRequest(
        @NotBlank
        String name,

        @NotNull
        Gender gender,

        @NotNull
        @Schema(example = "2025-03-02T19:53:58")
        @JsonFormat(pattern = DateTimeConstants.DEFAULT_FORMAT, timezone = DateTimeConstants.DEFAULT_TIMEZONE)
        Instant birthDate
) {
}
