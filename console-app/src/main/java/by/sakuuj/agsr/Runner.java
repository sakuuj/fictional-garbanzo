package by.sakuuj.agsr;

import by.sakuuj.agsr.domain.LoginResponse;
import by.sakuuj.agsr.domain.PatientRequest;
import by.sakuuj.agsr.domain.UserArgs;
import by.sakuuj.agsr.parser.ArgsParser;
import by.sakuuj.agsr.service.Generator;
import by.sakuuj.agsr.service.WebAppClient;
import by.sakuuj.agsr.service.WebAppClientImpl;
import by.sakuuj.agsr.service.GeneratorImpl;

import java.util.List;

public class Runner {

    public static final int PATIENTS_COUNT = 100;

    public static void main(String[] args) {

        UserArgs userArgs = new ArgsParser().parseArgs(args);

        WebAppClient webAppClient = new WebAppClientImpl(userArgs.baseUri());
        LoginResponse loginResponse = webAppClient.login(userArgs.userCredentials());

        Generator generator = new GeneratorImpl();
        List<PatientRequest> patients = generator.generatePatients(PATIENTS_COUNT);

        webAppClient.createPatients(loginResponse, patients);

        System.out.println("Patients have been successfully created!");
    }
}
