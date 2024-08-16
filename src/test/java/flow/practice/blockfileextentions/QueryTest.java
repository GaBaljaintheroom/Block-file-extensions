package flow.practice.blockfileextentions;


import flow.practice.blockfileextentions.global.config.JpaAuditingConfig;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;


@DataJpaTest
@Testcontainers
@Import(value = JpaAuditingConfig.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class QueryTest {

}

