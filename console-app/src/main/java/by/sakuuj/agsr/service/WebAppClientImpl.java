package by.sakuuj.agsr.service;

import by.sakuuj.agsr.domain.LoginRequest;
import by.sakuuj.agsr.domain.LoginResponse;
import by.sakuuj.agsr.domain.PatientRequest;
import by.sakuuj.agsr.domain.UserCredentials;
import by.sakuuj.agsr.mapper.JsonMapper;
import by.sakuuj.agsr.util.FutureUtil;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WebAppClientImpl implements WebAppClient {

    private final URI baseUri;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
    private final JsonMapper jsonMapper = new JsonMapper();

    private static final String PATIENTS_URI = "/api/v1/patients";
    private static final String AUTH_LOGIN_URI = "/api/v1/auth/login";

    public WebAppClientImpl(URI baseUri) {

        this.baseUri = baseUri;
    }

    public LoginResponse login(UserCredentials userCredentials) {

        Objects.requireNonNull(userCredentials);

        HttpRequest loginHttpRequest = buildLoginHttpRequest(userCredentials);

        HttpResponse<String> httpResponse = sendHttpRequest(loginHttpRequest);

        if (httpResponse.statusCode() != 200) {
            throw new IllegalStateException("Error when trying to login: " + httpResponse);
        }

        return jsonMapper.fromJson(httpResponse.body(), LoginResponse.class);
    }

    public void createPatients(LoginResponse loginResponse, List<PatientRequest> patients) {

        Objects.requireNonNull(patients);

        List<? extends Future<?>> futures = patients.stream()
                .map(p -> executorService.submit(() -> createPatient(loginResponse, p)))
                .toList();

        futures.forEach(FutureUtil::waitForFuture);
    }

    private HttpRequest buildLoginHttpRequest(UserCredentials userCredentials) {

        var loginRequest = LoginRequest.builder()
                .username(userCredentials.username())
                .password(userCredentials.password())
                .build();

        var destinationURI = URI.create(baseUri.toString() + AUTH_LOGIN_URI);

        String jsonRequest = jsonMapper.toJson(loginRequest);

        return HttpRequest.newBuilder()
                .uri(destinationURI)
                .header("Content-Type", "application/json; charset=utf-8")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();
    }

    private HttpResponse<String> sendHttpRequest(HttpRequest httpRequest) {
        try {
            return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void createPatient(LoginResponse loginResponse, PatientRequest patientRequest) {

        Objects.requireNonNull(patientRequest);

        HttpRequest httpRequest = buildCreatePatientHttpRequest(loginResponse, patientRequest);
        HttpResponse<String> httpResponse = sendHttpRequest(httpRequest);

        if (httpResponse.statusCode() != 201) {

            throw new IllegalStateException("""
                    Error when trying to create a Patient
                    %s
                    %s""".formatted(httpResponse, httpResponse.body())
            );
        }
    }

    private HttpRequest buildCreatePatientHttpRequest(LoginResponse loginResponse, PatientRequest patientRequest) {

        var destinationURI = URI.create(baseUri.toString() + PATIENTS_URI);

        String jsonRequest = jsonMapper.toJson(patientRequest);

        return HttpRequest.newBuilder()
                .uri(destinationURI)
                .header("Authorization", "Bearer " + loginResponse.accessToken())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();
    }
}
