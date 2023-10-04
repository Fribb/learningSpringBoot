package net.fribbtastic.learningSpringBoot.employee;

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

    /**
     * Test the getAll Method for a response with two elements
     */
    @DisplayName("Test: getAll() [positive]")
    @Test
    public void testGetAllEmployees() {
        Mockito.when(this.repository.findAll()).thenReturn(this.employeeList);                                          // Stub the findAll method of the Repository

        List<Employee> employeeList = this.service.getAll();

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
    @DisplayName("Test: getAll() [negative]")
    @Test
    public void testGetAllEmployee_Empty() {
        Mockito.when(this.repository.findAll()).thenReturn(Collections.emptyList());                                    // Stub the findAll method with an Empty List

        List<Employee> employeeList = this.service.getAll();

        Assertions.assertThat(employeeList).isNotNull();                                                                // Assert that the List is not null
        Assertions.assertThat(employeeList).isEmpty();                                                                  // Assert that the List is empty

    }

}