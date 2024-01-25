package net.fribbtastic.learningSpringBoot.employee;

import net.fribbtastic.learningSpringBoot.exceptions.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Frederic Eßer
 */
@ExtendWith({MockitoExtension.class, SpringExtension.class}) // tell JUnit that we want to extend the test with Mockito and Spring
@WebMvcTest(controllers = EmployeeController.class) // here we want to restrict the context to only the EmployeeController
public class EmployeeControllerUnitTest {

    @Autowired // let Spring handle dependency injection to test the MVC application
    private MockMvc mockMvc;

    @MockBean // Mock our service implementation
    private EmployeeServiceImpl service;

    private final List<Employee> employeeList = Arrays.asList(
            new Employee(1L, "Test FirstName 01", "Test LastName 01"),
            new Employee(2L, "Test FirstName 02", "Test LastName 02")
    );

    private final Employee employee = new Employee(3L,"Test FirstName 03", "Test LastName 03");

    /**
     * Test the getAll method of the service through a WebMVC Test
     * The returned List should contain 2 Elements with firstName and lastName
     *
     * @throws Exception - Exception thrown by MockMvc
     */
    @DisplayName("Test [WebMVC]: get all Employees")
    @Test
    public void testMvcGetAll() throws Exception {

        Mockito.when(this.service.getAllEmployees()).thenReturn(this.employeeList);                                                      // stub the getAll method with the prepared List

        this.mockMvc.perform(MockMvcRequestBuilders.get("/employee").accept(MediaType.APPLICATION_JSON))              // trigger the '/employee' endpoint with 'application/json' content type
                .andDo(MockMvcResultHandlers.print())                                                                           // print the result
                .andExpect(MockMvcResultMatchers.status().isOk())                                                               // status needs to be OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200))                                // expect the status element to be correct
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.[0]").exists())                                            // there should be a first element
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.[0].firstName").value("Test FirstName 01"))    // firstName should be the expected value
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.[0].lastName").value("Test LastName 01"))      // lastName should be the expected value
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.[1]").exists())                                            // there should be a second element
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.[1].firstName").value("Test FirstName 02"))    // firstName should be the expected value
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.[1].lastName").value("Test LastName 02"));     // lastName should be the expected value

        Mockito.verify(this.service, Mockito.times(1)).getAllEmployees();                                               // Verify that the getAll Method of the Service was called one time
    }

    /**
     * Test the getAll method of the service through a WebMVC Test
     * The returned List should be empty
     *
     * @throws Exception - Exception thrown my MockMvc
     */
    @DisplayName("Test [WebMVC]: get all Employees (empty list)")
    @Test
    public void testMvcGetAll_Empty() throws Exception {
        Mockito.when(this.service.getAllEmployees()).thenReturn(Collections.emptyList());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/employee").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200))                                // expect the status element to be correct
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());                                             // this time we expect the list to be empty

        Mockito.verify(this.service, Mockito.times(1)).getAllEmployees();                                               // Verify that the getAll Method of the Service was called one time
    }

    /**
     * Test to get a single Employee by its ID
     *
     * @throws Exception Exception thrown by MockMVC
     */
    @DisplayName("Test [WebMVC]: get one Employee")
    @Test
    public void testMvcGetOne() throws Exception {
        Mockito.when(this.service.getEmployee(this.employee.getId())).thenReturn(this.employee);                             // stub the getOne Method to return a pre-defined Employee

        this.mockMvc.perform(MockMvcRequestBuilders.get("/employee/" + this.employee.getId()).accept(MediaType.APPLICATION_JSON))   // request that specific employee
                .andDo(MockMvcResultHandlers.print())                                                                                         // print the output
                .andExpect(MockMvcResultMatchers.status().isOk())                                                                             // Expect that the status code is OK (200)
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200))                                        // Expect that the status element is correct
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())                                                        // Expect that the data element exists
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.firstName").value("Test FirstName 03"))                // Expect that the firstName is correct
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.lastName").value("Test LastName 03"));                 // Expect that the lastName is correct

        Mockito.verify(this.service, Mockito.times(1)).getEmployee(this.employee.getId());                                   // Verify that the getOne Method of the Service was called one time
    }

    /**
     * Test to get a single Employee by its ID that doesn't exist
     *
     * @throws Exception - Exception thrown by MockMVC
     */
    @DisplayName("Test [WebMVC]: get one missing Employee")
    @Test
    public void testMvcGetOne_Empty() throws Exception {

        long id = 4L;                                                                                                                                                 // save the ID so that it is easier to change

        Mockito.when(this.service.getEmployee(id)).thenThrow(new EntityNotFoundException(id));                                                                             // Stub the getOne Method to throw an Exception when we call it with the ID

        this.mockMvc.perform(MockMvcRequestBuilders.get("/employee/" + id).accept(MediaType.APPLICATION_JSON))                                              // request the non-existing employee
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())                                                                                               // Expect that the Status is 404 Not Found
                .andExpect(result -> Assertions.assertThat(result.getResolvedException())
                        .isInstanceOf(EntityNotFoundException.class)                                                                                                  // Assert that the resolved Exception is the EntityNotFoundException
                        .hasMessage("Entity with the ID '4' could not be found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(404))                                                                // The Code is set as expected
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").exists())                                                                               // Expect that there is an error element
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.type").value(EntityNotFoundException.class.getSimpleName()))                              // The message is set as expected
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("Entity not found"))                                          // The message is set as expected
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.details").value("Entity with the ID '4' could not be found"))                 // The details is set as expected
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.timestamp").isNotEmpty());                                                                // The timestamp is not empty (since the timestamp could/would be different between the time it was set in the response and in the test)

        Mockito.verify(this.service, Mockito.times(1)).getEmployee(id);                                                                               // Verify that the getOne Method of the Service was called one time
    }
}
