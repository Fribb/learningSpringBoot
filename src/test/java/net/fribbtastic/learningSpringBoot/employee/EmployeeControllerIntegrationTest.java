package net.fribbtastic.learningSpringBoot.employee;

import net.fribbtastic.learningSpringBoot.LearningSpringBootApplication;
import net.fribbtastic.learningSpringBoot.exceptions.EntityNotFoundException;
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

    /**
     * Integration test to get all Employee from the Employee endpoint
     */
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

    /**
     * Integration Test to get an Empty list of Employees
     */
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

    /**
     * Integration Test to get one specific Employee from the Employee Endpoint
     */
    @DisplayName("Test [Integration]: get one Employee")
    @Sql({"classpath:employee/truncate.sql"})
    @Sql({"classpath:employee/insert.sql"})
    @Test
    public void testGetOneEmployee() {

        long id = 1L;

        // Make the GET request to the API for the Employee with a specific ID
        ResponseEntity<ApiResponse<Employee>> response = this.template.exchange("/employee/" + id, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(200);                                 // assert that the response status code is 200
        Assertions.assertThat(response.getBody()).isNotNull();                                                          // assert that the response body is not null
        Assertions.assertThat(response.getBody().getStatusCode()).isEqualTo(200);                               // assert that the JSON element statusCode is 200
        Assertions.assertThat(response.getBody().getData()).isNotNull();                                                // assert that the JSON element data is not null
        Assertions.assertThat(response.getBody().getData().getFirstName()).isEqualTo("Test FirstName 01");      // assert that the firstName is set as expected
        Assertions.assertThat(response.getBody().getData().getLastName()).isEqualTo("Test LastName 01");        // assert that the lastName is set as expected

    }

    /**
     * Integration Test to get a missing Employee
     */
    @DisplayName("Test [Integration]: get one missing Employee")
    @Sql({"classpath:employee/truncate.sql"})
    @Test
    public void testGetOneEmployee_Empty() {

        long id = 1L;

        // Make the GET request to the API for the Employee with a specific ID
        ResponseEntity<ApiResponse<Employee>> response = this.template.exchange("/employee/" + id, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(404);                                 // assert that the response status code is 404
        Assertions.assertThat(response.getBody()).isNotNull();                                                          // assert that the response body is not null
        Assertions.assertThat(response.getBody().getStatusCode()).isEqualTo(404);                               // assert that the JSON element statusCode is 404
        Assertions.assertThat(response.getBody().getErrorDetails()).isNotNull();                                        // assert that there is an error JSON element
        Assertions.assertThat(response.getBody().getErrorDetails().getType()).isEqualTo(EntityNotFoundException.class.getSimpleName()); // assert that the type JSON element is set as expected
        Assertions.assertThat(response.getBody().getErrorDetails().getMessage()).isEqualTo("Entity not found"); // assert that the message JSON element is set ass expected
        Assertions.assertThat(response.getBody().getErrorDetails().getDetails()).isEqualTo("Entity with the ID '" + id + "' could not be found"); // assert that the details JSON element is set as expected
        Assertions.assertThat(response.getBody().getErrorDetails().getTimestamp()).isNotEmpty();                        // assert that the timestamp JSON element is not empty

    }
}
