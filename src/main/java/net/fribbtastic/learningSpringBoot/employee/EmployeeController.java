package net.fribbtastic.learningSpringBoot.employee;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping // Just like the RequestMapping, we can define a mapping here, GET in this case
    public List<Employee> getAllEmployees() {
        return this.employeeService.getAll(); // call the "getAll" Method from the service
    }
}
