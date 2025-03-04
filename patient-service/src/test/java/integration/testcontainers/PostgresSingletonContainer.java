package integration.testcontainers;

import lombok.experimental.UtilityClass;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;


@UtilityClass
public class PostgresSingletonContainer {

    private static final String POSTGRES_IMAGE = "postgres:16.8-alpine3.20";

    private static final PostgreSQLContainer<?> POSTGRES_CONTAINER = new PostgreSQLContainer<>(POSTGRES_IMAGE)
            .withDatabaseName("TEST_DB")
            .withUsername("TEST_USERNAME")
            .withPassword("TEST_PASSWORD");

    static {
        POSTGRES_CONTAINER.start();
    }

    public static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);
    }
}