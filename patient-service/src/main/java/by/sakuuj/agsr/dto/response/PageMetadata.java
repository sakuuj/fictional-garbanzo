package by.sakuuj.agsr.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record PageMetadata(
        @Schema(example = "0", description = "Returned page number")
        int pageNumber,

        @Schema(example = "20", description = "Returned page size")
        int pageSize,

        @Schema(
                example = "13",
                description = "Total number of pages available if using the size as specified in 'pageSize'"
        )
        int totalPages,

        @Schema(example = "643", description = "Total number of elements available")
        int totalElements
) {
}
