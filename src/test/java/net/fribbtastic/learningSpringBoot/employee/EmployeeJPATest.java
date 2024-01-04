package net.fribbtastic.learningSpringBoot.employee;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

/**
 * @author Frederic Eßer
 */
@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class EmployeeJPATest {
    @Autowired private EmployeeRepository repository;

    private Employee employee;

    /**
     * Create a new Employee Object before each test
     */
    @BeforeEach
    public void setupEach() {
        this.employee = Employee.builder()
                .firstName("Test First Name")
                .lastName("Test Last Name")
                .build();
    }

    /**
     * Test that the Injected Components are not null
     */
    @Test
    @DisplayName("Test: Injected Components are not Null")
    void testInjectedComponentsAreNotNull() {
        Assertions.assertThat(this.repository).isNotNull();
    }

    /**
     * Test to save a new Employee in the Database
     */
    @Test
    @DisplayName("Test: Employee is saved")
    public void testSavedEmployee() {

        Employee savedEmployee = this.repository.save(this.employee);                       // save the employee in the database

        Assertions.assertThat(savedEmployee).isNotNull();                                   // assert that the saved Employee is not null
        Assertions.assertThat(savedEmployee.getId()).isGreaterThan(0);                // assert that the ID of the saved Employee is greater than 0
    }

    /**
     * Test to get all the Employees from the Database
     */
    @Test
    @DisplayName("Test: get all Employees")
    public void testGetAllEmployees() {

        this.repository.save(this.employee);                                    // Save the Employee that was build in the BeforeEach

        Employee e1 = Employee.builder()                                        // Build a second Employee
                .firstName("Test First Name 2")
                .lastName("Test Last Name 2")
                .build();

        this.repository.save(e1);                                               // Save that second Employee

        List<Employee> employeeList = this.repository.findAll();                // find all Employees in the Database

        Assertions.assertThat(employeeList).isNotNull();                        // Assert that the List is not Null
        Assertions.assertThat(employeeList.size()).isEqualTo(2);        // Assert that the Size of the List is 2 elements long
    }

    /**
     * Test to get an Employee by the ID
     */
    @Test
    @DisplayName("Test: get Employee by ID")
    public void testGetEmployeeById() {

        this.repository.save(this.employee);                                                    // Save the Employee that was build in the BeforeEach

        Employee empDB = this.repository.findById(this.employee.getId()).orElse(null);    // find the Employee with by ID or null if not present

        Assertions.assertThat(empDB).isNotNull();                                               // Assert that the employee is not null
        Assertions.assertThat(empDB.getFirstName()).isEqualTo("Test First Name");       // Assert that the First Name of the Employee is correct
        Assertions.assertThat(empDB.getLastName()).isEqualTo("Test Last Name");         // Assert that the Last Name of the Employee is correct
    }

    /**
     * Test to create a new Employee and then Update the First and Last Name
     */
    @Test
    @DisplayName("Test: update Employee")
    public void testUpdateEmployee() {

        this.repository.save(this.employee);                                                       // Save the Employee that was build in the BeforeEach

        Employee savedEmp = this.repository.findById(this.employee.getId()).orElse(null);

        Assertions.assertThat(savedEmp).isNotNull();                                               // Assert that the employee is not null
        Assertions.assertThat(savedEmp.getFirstName()).isEqualTo("Test First Name");       // Assert that the First Name of the Employee is correct
        Assertions.assertThat(savedEmp.getLastName()).isEqualTo("Test Last Name");         // Assert that the Last Name of the Employee is correct

        savedEmp.setFirstName("Test First Name 01");                                                // Change the First Name of the Employee
        savedEmp.setLastName("Test Last Name 01");                                                  // change the Last Name of the Employee

        Employee updatedEmp = this.repository.save(savedEmp);                                       // Save the changes

        Assertions.assertThat(updatedEmp).isNotNull();                                              // Assert that the updated Employee is not null
        Assertions.assertThat(updatedEmp.getFirstName()).isEqualTo("Test First Name 01");   // Assert that the updated Employee has the correct First Name
        Assertions.assertThat(updatedEmp.getLastName()).isEqualTo("Test Last Name 01");     // Assert that the updated Employee has the correct Last Name
    }

    /**
     * Test to delete an Employee
     */
    @Test
    @DisplayName("Test: delete Employee")
    public void testDeleteEmployee() {

        this.repository.save(this.employee);                                                       // Save the Employee that was build in the BeforeEach

        this.repository.deleteById(this.employee.getId());                                          // delete the Employee

        Optional<Employee> employeeOptional = this.repository.findById(this.employee.getId());      // find the Employee that was just deleted (therefore the Employee Object wrapped in Optional)

        Assertions.assertThat(employeeOptional).isEmpty();                                          // Assert that the Optional<Employee> is Empty

    }
}
