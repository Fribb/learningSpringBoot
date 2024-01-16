package net.fribbtastic.learningSpringBoot.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Frederic Eßer
 */
@RestController // identifier for Spring that this is a Controller for the restful web services
@RequestMapping(path = "/employee", produces = "application/json") // the definition of the mapping and that this controller is accessible on "/employee" and that it will produce a JSON response
public class EmployeeController {

    @Autowired // Spring will handle the dependency injection for us
    private EmployeeServiceImpl employeeService;

    /**
     * Get all Employees
     *
     * @return - a List of Employee Objects in a ResponseEntity
     */
    @GetMapping // Just like the RequestMapping, we can define a mapping here, GET in this case
    public ResponseEntity<List<Employee>> getAllEmployees() {

        List<Employee> employeeList = this.employeeService.getAll(); // call the getAll Method from the service to get all Employees

        return new ResponseEntity<>(employeeList, HttpStatus.OK);   // Construct the Response Entity and return it
    }

    /**
     * get a single Employee by its ID
     *
     * @param id - the unique ID of the Employee
     * @return -
     */
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getOneEmployee(@PathVariable Long id) {

        Employee employee = this.employeeService.getOne(id);    // call the getOne Method from the service to get a single Employee from it

        return new ResponseEntity<>(employee, HttpStatus.OK);   // construct the Response Entity and return it
    }
}
