package by.sakuuj.agsr.domain;

import lombok.Builder;

import java.net.URI;

@Builder
public record UserArgs(
        UserCredentials userCredentials,
        URI baseUri
) {
}
