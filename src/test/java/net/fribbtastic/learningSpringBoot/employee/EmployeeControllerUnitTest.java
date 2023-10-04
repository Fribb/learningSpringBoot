package net.fribbtastic.learningSpringBoot.employee;

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

    /**
     * Test the getAll method of the service through a WebMVC Test
     * The returned List should contain 2 Elements with firstName and lastName
     *
     * @throws Exception - Exception thrown by MockMvc
     */
    @DisplayName("Test [WebMVC]: getAll() [positive]")
    @Test
    public void testMvcGetAll() throws Exception {

        Mockito.when(this.service.getAll()).thenReturn(this.employeeList);                                                      // stub the getAll method with the prepared List

        this.mockMvc.perform(MockMvcRequestBuilders.get("/employee").accept(MediaType.APPLICATION_JSON))              // trigger the '/employee' endpoint with 'application/json' content type
                .andDo(MockMvcResultHandlers.print())                                                                           // print the result
                .andExpect(MockMvcResultMatchers.status().isOk())                                                               // status needs to be OK
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists())                                            // there should be a first element
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("Test FirstName 01"))    // firstName should be the expected value
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("Test LastName 01"))      // lastName should be the expected value
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]").exists())                                            // there should be a second element
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName").value("Test FirstName 02"))    // firstName should be the expected value
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName").value("Test LastName 02"));     // lastName should be the expected value
    }

    /**
     * Test the getAll method of the service through a WebMVC Test
     * The returned List should be empty
     *
     * @throws Exception - Exception thrown my MockMvc
     */
    @DisplayName("Test [WebMVC]: getAll() [negative]")
    @Test
    public void testMvcGetAll_Empty() throws Exception {
        Mockito.when(this.service.getAll()).thenReturn(Collections.emptyList());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/employee").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());                                             // this time we expect the list to be empty
    }
}
