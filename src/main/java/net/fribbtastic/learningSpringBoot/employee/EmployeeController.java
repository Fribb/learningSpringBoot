package net.fribbtastic.learningSpringBoot.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Frederic Eßer
 */
@RestController // identifier for Spring that this is a Controller for the RESTful web services
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

        ResponseEntity<List<Employee>> response = new ResponseEntity<>(employeeList, HttpStatus.OK); // Construct the Response Entity

        return response;
    }
}
