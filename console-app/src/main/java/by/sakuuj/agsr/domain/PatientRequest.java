package by.sakuuj.agsr.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

@Builder
@ToString
@Getter
public class PatientRequest {

    private String name;
    private String gender;
    private Instant birthDate;

    public String gender() {
        return gender;
    }

    public Instant birthDate() {
        return birthDate;
    }

    public String name() {
        return name;
    }
}


