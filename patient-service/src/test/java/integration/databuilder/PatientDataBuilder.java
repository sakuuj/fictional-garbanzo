package integration.databuilder;

import by.sakuuj.agsr.dto.request.PatientRequest;
import by.sakuuj.agsr.dto.response.PatientResponse;
import by.sakuuj.agsr.entity.PatientEntity;
import by.sakuuj.agsr.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.Instant;
import java.util.UUID;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "newInstance")
public class PatientDataBuilder {

    private UUID id = UUID.fromString("4fe75c15-5099-475e-901c-aed6e473b051");
    private String name = "Andy Warhol";
    private Gender gender = Gender.MALE;
    private Instant birthDate = Instant.parse("1928-08-06T00:00:00.00Z");
    private short version = 0;

    public PatientEntity buildEntity() {

        return PatientEntity.builder()
                .id(id)
                .name(name)
                .gender(gender)
                .birthDate(birthDate)
                .version(version)
                .build();
    }

    public PatientRequest buildRequest() {

        return PatientRequest.builder()
                .name(name)
                .gender(gender)
                .birthDate(birthDate)
                .build();
    }

    public PatientResponse buildResponse() {

        return PatientResponse.builder()
                .name(name)
                .gender(gender)
                .birthDate(birthDate)
                .build();
    }
}
