package net.fribbtastic.learningSpringBoot.employee;

import net.fribbtastic.learningSpringBoot.LearningSpringBootApplication;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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

    @DisplayName("Test [Integration]: getAll() [positive]")
    @Sql({"classpath:employee/truncate.sql"}) // run a database script when executing this test
    @Sql({"classpath:employee/insert.sql"})
    @Test
    public void testGetAllEmployees() {
        ResponseEntity<Employee[]> response = this.template.getForEntity("http://localhost:" + this.port + "/employee", Employee[].class);

        Assertions.assertThat(response.getBody()).isNotNull();                                                          // Assert that the Body of the Response is not Null (Fail-Fast)

        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(200);                                 // Assert that the response has HTTP Status Code OK
        Assertions.assertThat(response.getBody().length).isEqualTo(3);                                          // Assert that the number of Employees in the List are 3 elements
        Assertions.assertThat(response.getBody()[0].getFirstName()).isEqualTo("Test FirstName 01");             // Assert that the First Name of the first element is the expected value
        Assertions.assertThat(response.getBody()[0].getLastName()).isEqualTo("Test LastName 01");               // Assert that the Last Name of the first element is the expected value
    }

    @DisplayName("Test [Integration]: getAll() [negative]")
    @Sql({"classpath:employee/truncate.sql"})
    @Test
    public void testGetAllEmployees_Empty() {
        ResponseEntity<Employee[]> response = this.template.getForEntity("http://localhost:" + this.port + "/employee", Employee[].class);

        Assertions.assertThat(response.getBody()).isNotNull();                                                          // Assert that the Body of the Response is not Null (Fail-Fast)

        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(200);                                 // Assert that the response has HTTP Status Code OK
        Assertions.assertThat(response.getBody().length).isEqualTo(0);                                          // Assert that there are no employees in the list
    }
}
