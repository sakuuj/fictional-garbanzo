package integration.util;


import jakarta.persistence.EntityManager;
import lombok.experimental.UtilityClass;
import org.springframework.transaction.support.TransactionTemplate;

@UtilityClass
public class PostgresCleanerUtil {

    public static void cleanUp(TransactionTemplate txTemplate, EntityManager entityManager) {
        txTemplate.executeWithoutResult(txStatus -> {
            entityManager.createNativeQuery(
                    """
                            DO
                            $$BEGIN
                            EXECUTE  'TRUNCATE TABLE ' ||
                            (SELECT array_to_string(
                                (
                                SELECT ARRAY(SELECT table_name FROM information_schema.tables 
                                    WHERE table_schema = 'public' 
                                    AND table_name NOT LIKE 'databasechangelog%')
                                ), ','
                            ))::VARCHAR;
                            END$$
                            """).executeUpdate();
        });
    }
}

