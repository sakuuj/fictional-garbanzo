package by.sakuuj.agsr.controller;

import by.sakuuj.agsr.controller.swagger.PatientControllerSpecification;
import by.sakuuj.agsr.dto.request.PatientRequest;
import by.sakuuj.agsr.dto.response.PatientResponse;
import by.sakuuj.agsr.dto.response.PatientsPageResponse;
import by.sakuuj.agsr.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(
        value = "/api/v1/patients",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class PatientController implements PatientControllerSpecification {

    private final PatientService patientService;

    @GetMapping
    public PatientsPageResponse getAll(@PageableDefault Pageable pageable) {

        return patientService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public PatientResponse getById(@PathVariable("id") UUID id) {

        return patientService.getById(id);
    }

    @PostMapping
    public ResponseEntity<PatientResponse> create(@RequestBody @Valid PatientRequest createRequest) {

        PatientResponse createResponse = patientService.create(createRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, "/" + createResponse.id())
                .body(createResponse);
    }

    @PutMapping("/{id}")
    public PatientResponse update(@PathVariable("id") UUID id, @RequestBody @Valid PatientRequest updateRequest) {

        return patientService.update(id, updateRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") UUID id) {

        patientService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
