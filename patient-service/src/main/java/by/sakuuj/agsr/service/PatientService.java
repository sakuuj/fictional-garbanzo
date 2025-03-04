package by.sakuuj.agsr.service;

import by.sakuuj.agsr.constants.SecurityPermissions;
import by.sakuuj.agsr.dto.request.PatientRequest;
import by.sakuuj.agsr.dto.response.PatientResponse;
import by.sakuuj.agsr.dto.response.PatientsPageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.UUID;

import static by.sakuuj.agsr.constants.SecurityPermissions.PATIENT_DELETE;
import static by.sakuuj.agsr.constants.SecurityPermissions.PATIENT_READ;
import static by.sakuuj.agsr.constants.SecurityPermissions.PATIENT_WRITE;

public interface PatientService {

    @PreAuthorize("hasAuthority('" + PATIENT_READ + "')")
    PatientsPageResponse getAll(Pageable pageable);

    @PreAuthorize("hasAuthority('" + PATIENT_READ + "')")
    PatientResponse getById(UUID id);

    @PreAuthorize("hasAuthority('" + PATIENT_WRITE + "')")
    PatientResponse create(PatientRequest createRequest);

    @PreAuthorize("hasAuthority('" + PATIENT_WRITE + "')")
    PatientResponse update(UUID id, PatientRequest updateRequest);

    @PreAuthorize("hasAuthority('" + PATIENT_DELETE + "')")
    void deleteById(UUID id);
}
