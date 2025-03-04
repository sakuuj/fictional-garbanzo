package by.sakuuj.agsr.service.impl;

import by.sakuuj.agsr.dto.request.LoginRequest;
import by.sakuuj.agsr.dto.response.LoginResponse;
import by.sakuuj.agsr.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.endpoint.DefaultPasswordTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2PasswordGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final ClientRegistrationRepository clientRegistrationRepository;

    @SuppressWarnings("deprecation")
    private final DefaultPasswordTokenResponseClient passwordTokenResponseClient;

    @Override
    @SuppressWarnings("deprecation")
    public LoginResponse login(LoginRequest loginRequest) {

        ClientRegistration keycloakRegistration = clientRegistrationRepository.findByRegistrationId("keycloak");
        var passwordGrantRequest = new OAuth2PasswordGrantRequest(
                keycloakRegistration,
                loginRequest.username(),
                loginRequest.password()
        );

        OAuth2AccessTokenResponse tokenResponse = passwordTokenResponseClient.getTokenResponse(passwordGrantRequest);

        return LoginResponse.builder()
                .accessToken(tokenResponse.getAccessToken().getTokenValue())
                .build();
    }
}
