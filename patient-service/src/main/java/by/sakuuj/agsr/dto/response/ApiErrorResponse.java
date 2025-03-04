package by.sakuuj.agsr.dto.response;

import by.sakuuj.agsr.constants.DateTimeConstants;
import by.sakuuj.agsr.enums.ErrorCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.Instant;

@Builder
public record ApiErrorResponse(
        @Schema(enumAsRef = true)
        ErrorCode errorCode,

        @Schema(description = "Error message used for debugging purposes")
        String errorMessage,

        @Schema(example = "2025-03-02T19:53:58")
        @JsonFormat(pattern = DateTimeConstants.DEFAULT_FORMAT, timezone = DateTimeConstants.DEFAULT_TIMEZONE)
        Instant errorTime
) {
}
