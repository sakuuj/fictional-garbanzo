package by.sakuuj.agsr.controller.swagger;

import by.sakuuj.agsr.controller.swagger.util.ApiErrorBadRequest;
import by.sakuuj.agsr.dto.request.LoginRequest;
import by.sakuuj.agsr.dto.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Authentication")
public interface AuthenticationControllerSpecification {

    @Operation(summary = "Login and get access token")
    @ApiResponse(responseCode = "200")
    @ApiErrorBadRequest
    LoginResponse login(@RequestBody @Valid LoginRequest loginRequest);
}
