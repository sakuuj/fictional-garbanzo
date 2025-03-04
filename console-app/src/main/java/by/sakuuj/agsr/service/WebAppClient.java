package by.sakuuj.agsr.service;

import by.sakuuj.agsr.domain.LoginResponse;
import by.sakuuj.agsr.domain.PatientRequest;
import by.sakuuj.agsr.domain.UserCredentials;

import java.util.List;

public interface WebAppClient {

    void createPatients(LoginResponse loginResponse, List<PatientRequest> patients);
    LoginResponse login(UserCredentials userCredentials);
}
