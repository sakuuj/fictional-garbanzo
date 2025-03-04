package integration.by.sakuuj.agsr.api;

import by.sakuuj.agsr.ApplicationRunner;
import by.sakuuj.agsr.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import integration.testcontainers.PostgresSingletonContainer;
import integration.util.PostgresCleanerUtil;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.support.TransactionTemplate;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(classes = ApplicationRunner.class)
@EnableAutoConfiguration(exclude = {
        OAuth2ClientAutoConfiguration.class,
        OAuth2ResourceServerAutoConfiguration.class
})
public class AbstractApiTest {

    @MockitoBean
    private AuthenticationService authenticationService;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected TransactionTemplate transactionTemplate;

    @Autowired
    protected EntityManager testEntityManager;

    @DynamicPropertySource
    static void registerContainers(DynamicPropertyRegistry registry) {

        PostgresSingletonContainer.registerProperties(registry);
    }

    @AfterEach
    void cleanUp() {

        PostgresCleanerUtil.cleanUp(transactionTemplate, testEntityManager);
    }
}
