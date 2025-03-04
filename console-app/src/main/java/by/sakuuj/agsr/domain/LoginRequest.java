package by.sakuuj.agsr.domain;

import lombok.Builder;

@Builder
public record LoginRequest(
        String username,
        String password
) {
}
