package by.sakuuj.agsr.dto.response;


import lombok.Builder;

@Builder
public record LoginResponse(
        String accessToken
) {
}
