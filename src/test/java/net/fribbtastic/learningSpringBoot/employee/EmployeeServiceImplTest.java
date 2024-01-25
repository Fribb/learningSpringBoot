package net.fribbtastic.learningSpringBoot.employee;

import net.fribbtastic.learningSpringBoot.exceptions.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Frederic Eßer
 */
@ExtendWith(MockitoExtension.class) // We extend the JUnit Test with Mockito so that we can use Mocks and Stubbing in the test cases
class EmployeeServiceImplTest {
    @Mock // here we mock the Repository to be injected into the service
    private EmployeeRepository repository;

    @InjectMocks // The Mocks are injected here
    private EmployeeServiceImpl service;

    private final List<Employee> employeeList = Arrays.asList(
            new Employee(1L, "Test FirstName 01", "Test LastName 01"),
            new Employee(2L, "Test FirstName 02", "Test LastName 02")
    );

    private final Employee employee = new Employee(3L, "Test FirstName 03", "Test LastName 03");

    /**
     * Test the getAll Method for a response with two elements
     */
    @DisplayName("Test: get All Employees (2 results)")
    @Test
    public void testGetAllEmployees() {
        Mockito.when(this.repository.findAll()).thenReturn(this.employeeList);                                          // Stub the findAll method of the Repository

        List<Employee> employeeList = this.service.getAllEmployees();

        Assertions.assertThat(employeeList).isNotNull();                                                                // Assert that the list is not null
        Assertions.assertThat(employeeList.size()).isEqualTo(2);                                                // Assert that the list is exactly 2 elements long
        Assertions.assertThat(employeeList.get(0).getFirstName()).isEqualTo("Test FirstName 01");               // Assert that the first employee has the expected values
        Assertions.assertThat(employeeList.get(0).getLastName()).isEqualTo("Test LastName 01");
        Assertions.assertThat(employeeList.get(1).getFirstName()).isEqualTo("Test FirstName 02");               // Assert that the second employee has the expected values
        Assertions.assertThat(employeeList.get(1).getLastName()).isEqualTo("Test LastName 02");
    }

    /**
     * Test the getAll Method for an Empty response
     */
    @DisplayName("Test: get all Employees (0 results)")
    @Test
    public void testGetAllEmployee_Empty() {
        Mockito.when(this.repository.findAll()).thenReturn(Collections.emptyList());                                    // Stub the findAll method with an Empty List

        List<Employee> employeeList = this.service.getAllEmployees();

        Assertions.assertThat(employeeList).isNotNull();                                                                // Assert that the List is not null
        Assertions.assertThat(employeeList).isEmpty();                                                                  // Assert that the List is empty

    }

    /**
     * Test to get a single Employee from the service
     */
    @DisplayName("Test: get one Employee")
    @Test
    public void testGetOneEmployee() {

        Mockito.when(this.repository.findById(this.employee.getId())).thenReturn(Optional.of(this.employee));           // Stub the findById method of the repository to return the prepared Employee

        Employee emp = this.service.getEmployee(this.employee.getId());                                                      // get the Employee by its ID

        Assertions.assertThat(emp).isNotNull();                                                                         // Assert that the returned Employee isn't null
        Assertions.assertThat(emp.getId()).isEqualTo(3L);                                                       // Assert that the ID of the Employee is correct
        Assertions.assertThat(emp.getFirstName()).isEqualTo("Test FirstName 03");                               // Assert that the First Name is correct
        Assertions.assertThat(emp.getLastName()).isEqualTo("Test LastName 03");                                 // Assert that the Last Name is correct
    }

    /**
     * Test to get a non-existing Employee and assert that the correct Exception is being thrown
     */
    @DisplayName("Test: get one non-existing Employee")
    @Test
    public void testGetOneEmployee_Empty() {

        Mockito.when(this.repository.findById(4L)).thenReturn(Optional.empty());                                      //Stub the findById method of the repository to return an Empty response

        Assertions.assertThatThrownBy(() -> {                                                                           // Assert that an Exception will be thrown
            this.service.getEmployee(4L);                                                                                 // get the Employee with the non-existing ID 4L
        }).isInstanceOf(EntityNotFoundException.class)                                                                  // Assert that the thrown Exception is the expected Exception Class
                .hasMessage("Entity with the ID '4' could not be found");                                               // Assert that the Message of the Exception is the expected Message
    }

}