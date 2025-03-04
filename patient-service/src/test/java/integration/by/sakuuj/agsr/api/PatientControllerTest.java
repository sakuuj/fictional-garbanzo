package integration.by.sakuuj.agsr.api;

import by.sakuuj.agsr.constants.SecurityPermissions;
import by.sakuuj.agsr.dto.request.PatientRequest;
import by.sakuuj.agsr.dto.response.PatientResponse;
import by.sakuuj.agsr.entity.PatientEntity;
import integration.databuilder.PatientDataBuilder;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.json.JsonCompareMode;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PatientControllerTest extends AbstractApiTest {

    private static final String BASE_URL = "/api/v1/patients";

    @Nested
    class CreatePatientEndpoint {

        @Test
        @WithMockUser(authorities = SecurityPermissions.PATIENT_WRITE)
        void onCreate_whenRequestValid_shouldCreate() throws Exception {

            // given
            var dataBuilder = PatientDataBuilder.newInstance()
                    .withId(null);

            PatientRequest createRequest = dataBuilder.buildRequest();
            String createRequestJson = objectMapper.writeValueAsString(createRequest);

            PatientEntity expectedEntity = dataBuilder.buildEntity();

            PatientResponse expectedResponse = dataBuilder.buildResponse();

            // when, then
            MockHttpServletResponse response = mockMvc.perform(
                            post(BASE_URL)
                                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                                    .content(createRequestJson)
                    )
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isCreated())
                    .andReturn()
                    .getResponse();

            PatientResponse actualResponse = objectMapper.readValue(response.getContentAsString(), PatientResponse.class);
            assertThat(actualResponse)
                    .usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(expectedResponse);

            UUID createdPatientId = actualResponse.id();
            assertThat(response.getHeader(HttpHeaders.LOCATION))
                    .isEqualTo("/" + createdPatientId);

            transactionTemplate.executeWithoutResult(status -> {

                List<PatientEntity> foundPatients = testEntityManager.createQuery("FROM PatientEntity", PatientEntity.class)
                        .getResultList();

                assertThat(foundPatients).hasSize(1);

                PatientEntity actualPatient = foundPatients.getFirst();
                assertThat(actualPatient)
                        .usingRecursiveComparison()
                        .ignoringFields("id")
                        .isEqualTo(expectedEntity);

                assertThat(actualPatient.getId()).isEqualTo(createdPatientId);
            });
        }

        @Test
        @WithMockUser(authorities = SecurityPermissions.PATIENT_WRITE)
        void onCreate_whenRequestInvalid_shouldReturnBadRequest() throws Exception {
            // given
            PatientRequest createRequest = PatientDataBuilder.newInstance()
                    .withName(null)
                    .buildRequest();
            String createRequestJson = objectMapper.writeValueAsString(createRequest);

            // when, then
            mockMvc.perform(
                            post(BASE_URL)
                                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                                    .content(createRequestJson)
                    )
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().json("""
                            {
                                "errorCode": "INVALID_REQUEST"
                            }
                            """, JsonCompareMode.LENIENT)
                    );
        }

        @Test
        @WithMockUser
        void onCreate_whenNotAuthenticated_shouldReturnUnauthorized() throws Exception {
            // given
            PatientRequest createRequest = PatientDataBuilder.newInstance().buildRequest();
            String createRequestJson = objectMapper.writeValueAsString(createRequest);

            // when, then
            mockMvc.perform(
                            post(BASE_URL)
                                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                                    .content(createRequestJson)
                    )
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isForbidden())
                    .andExpect(content().json("""
                            {
                                "errorCode": "ACCESS_DENIED"
                            }
                            """, JsonCompareMode.LENIENT)
                    );
        }

        @Test
        void onCreate_whenNotAuthorized_shouldReturnForbidden() throws Exception {
            // given
            PatientRequest createRequest = PatientDataBuilder.newInstance().buildRequest();
            String createRequestJson = objectMapper.writeValueAsString(createRequest);

            // when, then
            mockMvc.perform(
                            post(BASE_URL)
                                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                                    .content(createRequestJson)
                    )
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isUnauthorized())
                    .andExpect(content().json("""
                            {
                                "errorCode": "AUTHENTICATION_ERROR"
                            }
                            """, JsonCompareMode.LENIENT)
                    );
        }
    }
}
