package by.sakuuj.agsr.domain;

import lombok.Builder;

@Builder
public record UserCredentials(
        String username,
        String password
) {
}
