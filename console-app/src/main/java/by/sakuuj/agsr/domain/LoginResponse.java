package by.sakuuj.agsr.domain;

import lombok.Builder;

@Builder
public record LoginResponse(
        String accessToken
) {
}
