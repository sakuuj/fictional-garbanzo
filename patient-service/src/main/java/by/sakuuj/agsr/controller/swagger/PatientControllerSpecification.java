package by.sakuuj.agsr.controller.swagger;

import by.sakuuj.agsr.controller.swagger.util.ApiErrorBadRequest;
import by.sakuuj.agsr.controller.swagger.util.ApiErrorForbidden;
import by.sakuuj.agsr.controller.swagger.util.ApiErrorNotFound;
import by.sakuuj.agsr.controller.swagger.util.ApiErrorUnauthorized;
import by.sakuuj.agsr.dto.request.PatientRequest;
import by.sakuuj.agsr.dto.response.PatientResponse;
import by.sakuuj.agsr.dto.response.PatientsPageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Tag(name = "Patients")
@SecurityRequirement(name = "Bearer Authentication")
public interface PatientControllerSpecification {

    @Operation(summary = "Get multiple patients")
    @ApiResponse(responseCode = "200")
    @ApiErrorBadRequest
    @ApiErrorUnauthorized
    @ApiErrorForbidden
    @Parameter(name = "size", example = "10", description = "Size of a page requested")
    @Parameter(name = "page", example = "0", description = "Number of a page requested (zero indexed)")
    PatientsPageResponse getAll(@Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "Get a patient")
    @ApiResponse(responseCode = "200")
    @ApiErrorBadRequest
    @ApiErrorUnauthorized
    @ApiErrorForbidden
    @ApiErrorNotFound
    PatientResponse getById(@Parameter(description = "Patient ID") UUID id);

    @Operation(summary = "Create a patient")
    @ApiResponse(responseCode = "201")
    @ApiErrorBadRequest
    @ApiErrorUnauthorized
    @ApiErrorForbidden
    ResponseEntity<PatientResponse> create(@Valid PatientRequest createRequest);

    @Operation(summary = "Update a patient")
    @ApiResponse(responseCode = "200")
    @ApiErrorBadRequest
    @ApiErrorUnauthorized
    @ApiErrorForbidden
    @ApiErrorNotFound
    PatientResponse update(@Parameter(description = "Patient ID") UUID id, @Valid PatientRequest updateRequest);

    @Operation(summary = "Delete a patient")
    @ApiResponse(responseCode = "204")
    @ApiErrorBadRequest
    @ApiErrorUnauthorized
    @ApiErrorForbidden
    ResponseEntity<Void> deleteById(@Parameter(description = "Patient ID") UUID id);
}
