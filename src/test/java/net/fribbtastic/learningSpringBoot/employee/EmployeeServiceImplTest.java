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

import java.util.*;

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

    /**
     * Test to create a new Employee
     */
    @DisplayName("Test: create a new Employee")
    @Test
    public void testCreateEmployee() {

        Employee savingEmp = new Employee(5L, "Test Employee FirstName 05", "Test Employee LastName 05");   // create a new Employee

        Mockito.when(this.repository.save(savingEmp)).thenReturn(savingEmp);                                            // Stub the save method to return the created employee

        Employee emp = this.service.createEmployee(savingEmp);                                                          // Create the new Employee

        Assertions.assertThat(emp).isNotNull();                                                                         // Assert that the created Employee is not null
        Assertions.assertThat(emp.getId()).isEqualTo(5L);                                                       // Assert that the ID is as expected
        Assertions.assertThat(emp.getFirstName()).isEqualTo("Test Employee FirstName 05");                      // Assert that the FirstName is as expected
        Assertions.assertThat(emp.getLastName()).isEqualTo("Test Employee LastName 05");                        // Assert that the LastName is as expected
    }

    /**
     * Test to update an existing Employee
     */
    @DisplayName("Test: update an existing Employee")
    @Test
    public void testUpdateEmployee() {
        long id = 6L;

        Employee empUpdate = new Employee(id, "Updated Test Employee FirstName 06", "Updated Test Employee LastName 06");   // Create a new employee Object

        Mockito.when(this.repository.findById(id)).thenReturn(Optional.of(empUpdate));                                          // Stub the findById method to return the updated employee
        Mockito.when(this.repository.save(empUpdate)).thenReturn(empUpdate);                                                    // Stub the save method to return the updated employee

        Employee updatedEmp = this.service.updateEmployee(id, empUpdate);                                                       // update the Employee

        Assertions.assertThat(updatedEmp).isNotNull();                                                                          // Assert that the created Employee is not null
        Assertions.assertThat(updatedEmp.getId()).isEqualTo(6L);                                                        // Assert that the ID is as expected
        Assertions.assertThat(updatedEmp.getFirstName()).isEqualTo("Updated Test Employee FirstName 06");               // Assert that the FirstName is as expected
        Assertions.assertThat(updatedEmp.getLastName()).isEqualTo("Updated Test Employee LastName 06");                 // Assert that the LastName is as expected
    }

    /**
     * Test to update an Employee that doesn't exist
     */
    @DisplayName("Test: update an Employee that doesn't exist")
    @Test
    public void testUpdateMissingEmployee() {

        long id = 7L;
        Employee empUpdate = new Employee(id, "Updated Test Employee FirstName 07", "Updated Test Employee LastName 07");

        Mockito.when(this.repository.findById(id)).thenReturn(Optional.empty());                                      //Stub the findById method of the repository to return an Empty response

        Assertions.assertThatThrownBy(() -> {                                                                           // Assert that an Exception will be thrown
                    this.service.updateEmployee(id, empUpdate);                                                         // try to update an Employee with the missing ID 7
                }).isInstanceOf(EntityNotFoundException.class)                                                          // Assert that the thrown Exception is the expected Exception Class
                .hasMessage("Entity with the ID '7' could not be found");                                               // Assert that the Message of the Exception is the expected Message
    }

    /**
     * Test to delete an Employee
     */
    @DisplayName("Test: delete existing Employee")
    @Test
    public void testDeleteEmployee() {
        long id = 8L;
        Employee deleteEmployee = new Employee(id, "Test Employee FirstName 08", "Test Employee LastName 08");  // create a new Employee that we want to delete

        Mockito.when(this.repository.findById(id)).thenReturn(Optional.of(deleteEmployee));                                      // since we search for the Employee first that we want to delete, we mock the findById to return the employee

        this.service.deleteEmployee(id);                                                                                         // call the service to delete the Employee

        Mockito.verify(this.repository, Mockito.times(1)).findById(id);                                    // verify that Mockito ran once through the findById method with the provided ID of the Employee
        Mockito.verify(this.repository, Mockito.times(1)).delete(deleteEmployee);                          // verify that Mockito ran once through the delete method with the provided Employee object

        // I don't think it is necessary to specifically test that the Employee was deleted because we are mocking the repository and services here
        // We would need to mock the findById with an empty response and then assert that the EntityNotFoundException was thrown.
        /*
        Mockito.when(this.repository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
                    this.service.getEmployee(id);
                }).isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Entity with the ID '8' could not be found");
         */
    }

    /**
     * Test to delete an Employee that doesn't exist
     */
    @DisplayName("Test: delete a missing Employee")
    @Test
    public void testDeleteMissingEmployee() {
        long id = 9L;

        Mockito.when(this.repository.findById(id)).thenReturn(Optional.empty());               // mock that we return and empty response when the findById is being called

        Assertions.assertThatThrownBy(() -> {                                                     // assert that an exception is being thrown
                    this.service.deleteEmployee(id);                                              // try to delete the employee with the provided ID
                }).isInstanceOf(EntityNotFoundException.class)                                    // expect that the Thrown exception is an Instance of the EntityNotFoundException
                .hasMessage("Entity with the ID '9' could not be found");                         // expect that the message of the thrown Exception is as stated

        Mockito.verify(this.repository, Mockito.times(1)).findById(id);     // verify that the findById method in the repository was called one time
    }

}