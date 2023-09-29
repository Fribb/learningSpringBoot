package net.fribbtastic.learningSpringBoot.employee;

import net.fribbtastic.learningSpringBoot.LearningSpringBootApplication;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

/**
 * @author Frederic Eßer
 */
@SpringBootTest(classes = LearningSpringBootApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIntegrationTest {

    @LocalServerPort // specify that this is the local server port that we use to run the test
    private int port;

    @Autowired
    private TestRestTemplate template;

    @Sql({"classpath:employee/data.sql"}) // run a database script when executing this test
    @Test
    public void testGetAllEmployees() {
        ResponseEntity<Employee[]> response = this.template.getForEntity("http://localhost:" + this.port + "/employee", Employee[].class);

        Assertions.assertThat(response.getBody().length).isEqualTo(3);
        Assertions.assertThat(response.getBody()[0].getFirstName()).isEqualTo("Test FirstName 01");
        Assertions.assertThat(response.getBody()[0].getLastName()).isEqualTo("Test LastName 01");
    }
}
