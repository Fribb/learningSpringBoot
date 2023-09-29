package net.fribbtastic.learningSpringBoot.employee;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Frederic Eßer
 */
@ExtendWith(MockitoExtension.class) // We extend the JUnit test with Mockito so that we can use Mocks and Stubbing in the test
public class EmployeeControllerUnitTest {

    @InjectMocks
    private EmployeeController controller;

    @Mock // here we mock the repository to inject into the controller
    private EmployeeRepository repository;

    /**
     * In this Test we are using 'stubbing' to simulate a response of the endpoint
     * This means that when we call the Employee Repository 'findAll' Method Mockito will return with our employeeList
     */
    @Test
    public void testFindAll() {
        Employee emp1 = new Employee(1L, "Test FirstName 01", "Test LastName 01");
        Employee emp2 = new Employee(2L, "Test FirstName 02", "Test LastName 02");

        List<Employee> employeeList = new ArrayList<>(Arrays.asList(emp1, emp2));

        Mockito.when(this.repository.findAll()).thenReturn(employeeList);

        List<Employee> result = this.controller.getAllEmployees();

        // Assert that the size of the List is 2 elements long
        Assertions.assertThat(result.size()).isEqualTo(2);
        // Assert that the first employee has the expected values
        Assertions.assertThat(result.get(0).getId()).isEqualTo(1L);
        Assertions.assertThat(result.get(0).getFirstName()).isEqualTo("Test FirstName 01");
        Assertions.assertThat(result.get(0).getLastName()).isEqualTo("Test LastName 01");
        // Assert that the second employee has the expected values
        Assertions.assertThat(result.get(1).getId()).isEqualTo(2L);
        Assertions.assertThat(result.get(1).getFirstName()).isEqualTo("Test FirstName 02");
        Assertions.assertThat(result.get(1).getLastName()).isEqualTo("Test LastName 02");
    }
}
