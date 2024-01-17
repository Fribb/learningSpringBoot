package net.fribbtastic.learningSpringBoot.employee;

import net.fribbtastic.learningSpringBoot.LearningSpringBootApplication;
import net.fribbtastic.learningSpringBoot.responses.ApiResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

/**
 * @author Frederic Eßer
 */
@SpringBootTest(classes = LearningSpringBootApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIntegrationTest {

    @LocalServerPort // specify that this is the local server port that we use to run the test
    private int port;

    @Autowired
    private TestRestTemplate template;

    @DisplayName("Test [Integration]: get All Employees")
    @Sql({"classpath:employee/truncate.sql"}) // run a database script when executing this test
    @Sql({"classpath:employee/insert.sql"})
    @Test
    public void testGetAllEmployees() {
        // Make the GET request
        ResponseEntity<ApiResponse<List<Employee>>> responseEntity = this.template.exchange("/employee", HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

        Assertions.assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);       // assert that the response status code is 200
        Assertions.assertThat(responseEntity.getBody()).isNotNull();                                // assert that the response body is not null
        Assertions.assertThat(responseEntity.getBody().getStatusCode()).isEqualTo(200);     // assert that the JSON element statusCode is 200
        Assertions.assertThat(responseEntity.getBody().getData()).isNotNull();                      // assert that the JSON element data is not null
        Assertions.assertThat(responseEntity.getBody().getData().size()).isEqualTo(3);      // assert that the JSON element data has 0 elements

        Assertions.assertThat(responseEntity.getBody().getData().get(0).getFirstName()).isEqualTo("Test FirstName 01");      // assert that the firstName is as expected
        Assertions.assertThat(responseEntity.getBody().getData().get(0).getLastName()).isEqualTo("Test LastName 01");       // assert that the lastName is as Expected

        Assertions.assertThat(responseEntity.getBody().getData().get(1).getFirstName()).isEqualTo("Test FirstName 02");      // assert that the firstName is as expected
        Assertions.assertThat(responseEntity.getBody().getData().get(1).getLastName()).isEqualTo("Test LastName 02");       // assert that the lastName is as Expected

        Assertions.assertThat(responseEntity.getBody().getData().get(2).getFirstName()).isEqualTo("Test FirstName 03");      // assert that the firstName is as expected
        Assertions.assertThat(responseEntity.getBody().getData().get(2).getLastName()).isEqualTo("Test LastName 03");       // assert that the lastName is as Expected
    }

    @DisplayName("Test [Integration]: get All Employees (empty list)")
    @Sql({"classpath:employee/truncate.sql"})
    @Test
    public void testGetAllEmployees_Empty() {
        // Make the GET request
        ResponseEntity<ApiResponse<List<Employee>>> responseEntity = this.template.exchange("/employee", HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

        Assertions.assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);       // assert that the response status code is 200
        Assertions.assertThat(responseEntity.getBody()).isNotNull();                                // assert that the response body is not null
        Assertions.assertThat(responseEntity.getBody().getStatusCode()).isEqualTo(200);     // assert that the JSON element statusCode is 200
        Assertions.assertThat(responseEntity.getBody().getData()).isNotNull();                      // assert that the JSON element data is not null
        Assertions.assertThat(responseEntity.getBody().getData().size()).isEqualTo(0);      // assert that the JSON element data has 0 elements
    }
}
