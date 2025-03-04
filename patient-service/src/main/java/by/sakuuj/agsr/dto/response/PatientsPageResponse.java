package by.sakuuj.agsr.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record PatientsPageResponse(
        PageMetadata pageMetadata,
        List<PatientResponse> patients
) {
}
