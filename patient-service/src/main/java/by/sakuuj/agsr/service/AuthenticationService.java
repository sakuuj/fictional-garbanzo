package by.sakuuj.agsr.service;

import by.sakuuj.agsr.dto.request.LoginRequest;
import by.sakuuj.agsr.dto.response.LoginResponse;
import org.springframework.security.access.prepost.PreAuthorize;

public interface AuthenticationService {

    LoginResponse login(LoginRequest loginRequest);
}
