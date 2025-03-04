package by.sakuuj.agsr.controller;

import by.sakuuj.agsr.controller.swagger.AuthenticationControllerSpecification;
import by.sakuuj.agsr.dto.request.LoginRequest;
import by.sakuuj.agsr.dto.response.LoginResponse;
import by.sakuuj.agsr.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        value = "/api/v1/auth",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationControllerSpecification {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {

        return authenticationService.login(loginRequest);
    }
}
