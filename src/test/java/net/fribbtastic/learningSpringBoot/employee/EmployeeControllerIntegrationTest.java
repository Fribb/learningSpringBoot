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
import org.springframework.http.HttpEntity;
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

    @DisplayName("Test [Integration]: create a new Employee")
    @Sql({"classpath:employee/truncate.sql"})
    @Test
    public void testCreateEmployee() {

        Employee newEmployee = new Employee("New Test Employee FirstName 01", "New Test Employee LastName 01");         // create a new Employee Object

        HttpEntity<Employee> httpEntity = new HttpEntity<>(newEmployee);                                                // create a HttpEntity for that Employee Object

        // now we make the POST request to the API to create the new Employee, we also expect that we get a Response in our defined APIResponse back that then has an Employee in the data element
        ResponseEntity<ApiResponse<Employee>> response = this.template.exchange("/employee", HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
        });

        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(201);                                 // assert that the response status code is 201 (CREATED)
        Assertions.assertThat(response.getBody()).isNotNull();                                                          // assert that the response body is not null
        Assertions.assertThat(response.getBody().getStatusCode()).isEqualTo(201);                               // assert that the JSON element statusCode is also 201
        Assertions.assertThat(response.getBody().getData()).isNotNull();                                                // assert that the JSON element data is present
        Assertions.assertThat(response.getBody().getData().getId()).isNotNull();                                        // assert that the JSON element ID is present
        Assertions.assertThat(response.getBody().getData().getId().toString()).isNotEmpty();                            // assert that the ID is not empty
        Assertions.assertThat(response.getBody().getData().getFirstName()).isEqualTo("New Test Employee FirstName 01");     // assert that the FirstName is set as expected
        Assertions.assertThat(response.getBody().getData().getLastName()).isEqualTo("New Test Employee LastName 01");       // assert that the LastName is set as expected

    }

    @DisplayName("Test [Integration]: update an Employee")
    @Sql({"classpath:employee/insert.sql"})
    @Test
    public void testUpdateEmployee() {
        Employee updateEmployee = new Employee("Test Updated FirstName 01", "Test Updated LastName 01");

        HttpEntity<Employee> httpEntity = new HttpEntity<>(updateEmployee);

        ResponseEntity<ApiResponse<Employee>> response = this.template.exchange("/employee/" + 1L, HttpMethod.PUT, httpEntity, new ParameterizedTypeReference<>() {
        });

        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(200);                                 // assert that the response status code is 201 (CREATED)
        Assertions.assertThat(response.getBody()).isNotNull();                                                          // assert that the response body is not null
        Assertions.assertThat(response.getBody().getStatusCode()).isEqualTo(200);                               // assert that the JSON element statusCode is also 201
        Assertions.assertThat(response.getBody().getData()).isNotNull();                                                // assert that the JSON element data is present
        Assertions.assertThat(response.getBody().getData().getId()).isNotNull();                                        // assert that the JSON element ID is present
        Assertions.assertThat(response.getBody().getData().getId().toString()).isNotEmpty();                            // assert that the ID is not empty
        Assertions.assertThat(response.getBody().getData().getFirstName()).isEqualTo("Test Updated FirstName 01");     // assert that the FirstName is set as expected
        Assertions.assertThat(response.getBody().getData().getLastName()).isEqualTo("Test Updated LastName 01");       // assert that the LastName is set as expected
    }

    @DisplayName("Test [Integration]: update missing Employee")
    @Sql({"classpath:employee/truncate.sql"})
    @Test
    public void testGetOneEmployee_MissingEmployee() {

        Employee updateEmployee = new Employee("Test Updated FirstName 05", "Test Updated LastName 05");
        HttpEntity<Employee> httpEntity = new HttpEntity<>(updateEmployee);

        ResponseEntity<ApiResponse<Employee>> response = this.template.exchange("/employee/" + 5L, HttpMethod.PUT, httpEntity, new ParameterizedTypeReference<>() {
        });

        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(404);                                 // assert that the response status code is 404
        Assertions.assertThat(response.getBody()).isNotNull();                                                          // assert that the response body is not null
        Assertions.assertThat(response.getBody().getStatusCode()).isEqualTo(404);                               // assert that the JSON element statusCode is 404
        Assertions.assertThat(response.getBody().getErrorDetails()).isNotNull();                                        // assert that there is an error JSON element
        Assertions.assertThat(response.getBody().getErrorDetails().getType()).isEqualTo(EntityNotFoundException.class.getSimpleName()); // assert that the type JSON element is set as expected
        Assertions.assertThat(response.getBody().getErrorDetails().getMessage()).isEqualTo("Entity not found"); // assert that the message JSON element is set ass expected
        Assertions.assertThat(response.getBody().getErrorDetails().getDetails()).isEqualTo("Entity with the ID '" + 5L + "' could not be found"); // assert that the details JSON element is set as expected
        Assertions.assertThat(response.getBody().getErrorDetails().getTimestamp()).isNotEmpty();                        // assert that the timestamp JSON element is not empty

    }

    /**
     * Test to delete an existing Employee
     */
    @DisplayName("Test [Integration]: delete Employee")
    @Sql({"classpath:employee/insert.sql"})
    @Test
    public void testDeleteEmployee() {
        long id = 1L;

        // First, I want to make sure that we can actually get an employee with the ID
        ResponseEntity<ApiResponse<Employee>> beforeDeleteResponse = this.template.exchange("/employee/" + id, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        // Assert that there is an Employee and the data is as expected
        Assertions.assertThat(beforeDeleteResponse.getStatusCode().value()).isEqualTo(200);
        Assertions.assertThat(beforeDeleteResponse.getBody()).isNotNull();
        Assertions.assertThat(beforeDeleteResponse.getBody().getStatusCode()).isEqualTo(200);
        Assertions.assertThat(beforeDeleteResponse.getBody().getData()).isNotNull();
        Assertions.assertThat(beforeDeleteResponse.getBody().getData().getFirstName()).isEqualTo("Test FirstName 01");
        Assertions.assertThat(beforeDeleteResponse.getBody().getData().getLastName()).isEqualTo("Test LastName 01");

        // Now we delete the Employee
        ResponseEntity<ApiResponse<?>> deleteResponse = this.template.exchange("/employee/" + id, HttpMethod.DELETE, null, new ParameterizedTypeReference<>() {
        });

        // The response should be as expected
        Assertions.assertThat(deleteResponse.getStatusCode().value()).isEqualTo(200);
        Assertions.assertThat(deleteResponse.getBody()).isNotNull();
        Assertions.assertThat(deleteResponse.getBody().getStatusCode()).isEqualTo(200);
        Assertions.assertThat(deleteResponse.getBody().getData()).isNull();

        // Lastly, we (try to) get the Employee again to see if the element was actually deleted, this should then output an EntityNotFoundException
        ResponseEntity<ApiResponse<Employee>> afterDeleteResponse = this.template.exchange("/employee/" + id, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        // The response should be 404 and has an error JSON object
        Assertions.assertThat(afterDeleteResponse.getStatusCode().value()).isEqualTo(404);
        Assertions.assertThat(afterDeleteResponse.getBody()).isNotNull();
        Assertions.assertThat(afterDeleteResponse.getBody().getStatusCode()).isEqualTo(404);
        Assertions.assertThat(afterDeleteResponse.getBody().getErrorDetails()).isNotNull();
        Assertions.assertThat(afterDeleteResponse.getBody().getErrorDetails().getType()).isEqualTo(EntityNotFoundException.class.getSimpleName());
        Assertions.assertThat(afterDeleteResponse.getBody().getErrorDetails().getMessage()).isEqualTo("Entity not found");
        Assertions.assertThat(afterDeleteResponse.getBody().getErrorDetails().getDetails()).isEqualTo("Entity with the ID '" + id + "' could not be found");
        Assertions.assertThat(afterDeleteResponse.getBody().getErrorDetails().getTimestamp()).isNotEmpty();
    }

    /**
     * Test to delete an Employee that doesn't exist
     */
    @DisplayName("Test [Integration]: delete missing Employee")
    @Sql({"classpath:employee/truncate.sql"})
    @Test
    public void deleteMissingEmployee() {

        long id = 1L;

        // try to delete the Employee with an ID that doesn't exist
        ResponseEntity<ApiResponse<?>> response = this.template.exchange("/employee/" + id, HttpMethod.DELETE, null, new ParameterizedTypeReference<>() {
        });

        // the response should be a 404 and an EntityNotFoundException
        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(404);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getStatusCode()).isEqualTo(404);
        Assertions.assertThat(response.getBody().getErrorDetails()).isNotNull();
        Assertions.assertThat(response.getBody().getErrorDetails().getType()).isEqualTo(EntityNotFoundException.class.getSimpleName());
        Assertions.assertThat(response.getBody().getErrorDetails().getMessage()).isEqualTo("Entity not found");
        Assertions.assertThat(response.getBody().getErrorDetails().getDetails()).isEqualTo("Entity with the ID '" + id + "' could not be found");
        Assertions.assertThat(response.getBody().getErrorDetails().getTimestamp()).isNotEmpty();
    }
}
