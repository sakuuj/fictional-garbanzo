package by.sakuuj.agsr.service;

import by.sakuuj.agsr.domain.PatientRequest;

import java.util.List;

public interface Generator {

    PatientRequest generatePatient();

    List<PatientRequest> generatePatients(int count);
}
