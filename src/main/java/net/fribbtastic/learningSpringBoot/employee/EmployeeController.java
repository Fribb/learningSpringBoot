package net.fribbtastic.learningSpringBoot.employee;

import net.fribbtastic.learningSpringBoot.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Frederic Eßer
 */
@RestController // identifier for Spring that this is a Controller for the restful web services
@RequestMapping(path = "/employee", produces = "application/json")
// the definition of the mapping and that this controller is accessible on "/employee" and that it will produce a JSON response
public class EmployeeController {

    @Autowired // Spring will handle the dependency injection for us
    private EmployeeServiceImpl employeeService;

    /**
     * Get all Employees
     *
     * @return a List of Employee Objects in a ResponseEntity
     */
    @GetMapping // Just like the RequestMapping, we can define a mapping here, GET in this case
    public ResponseEntity<ApiResponse<List<Employee>>> getAllEmployees() {

        List<Employee> employeeList = this.employeeService.getAllEmployees(); // call the getAll Method from the service to get all Employees

        ApiResponse<List<Employee>> response = ApiResponse.createSuccessResponse(HttpStatus.OK, employeeList); // create a new success ApiResponse

        return ResponseEntity.status(HttpStatus.OK).body(response); // return the ApiResponse wrapped in a ResponseEntity
    }

    /**
     * get a single Employee by its ID
     *
     * @param id the unique ID of the Employee
     * @return the {@link ApiResponse} with the Employee wrapped in a {@link ResponseEntity}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> getEmployee(@PathVariable Long id) {

        Employee employee = this.employeeService.getEmployee(id);    // call the getOne Method from the service to get a single Employee from it

        ApiResponse<Employee> response = ApiResponse.createSuccessResponse(HttpStatus.OK, employee); // create a new success ApiResponse

        return ResponseEntity.status(HttpStatus.OK).body(response); // return the ApiResponse wrapped in a ResponseEntity
    }

    /**
     * Create a new Employee
     *
     * @param employee the employee information for creation
     * @return the {@link ApiResponse} with the created Employee Wrapped in a {@link ResponseEntity}
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Employee>> createEmployee(@RequestBody Employee employee) {
        Employee newEmployee = this.employeeService.createEmployee(employee);   // Call the createEmployee Method from the service to create a new Employee

        ApiResponse<Employee> response = ApiResponse.createSuccessResponse(HttpStatus.CREATED, newEmployee);   // create a new Success ApiResponse

        return ResponseEntity.status(HttpStatus.CREATED).body(response); // return the ApiResponse wrapped in a ResponseEntity
    }

    /**
     * Update an existing Employee
     *
     * @param id the ID of the employee that should be updated
     * @param employee the employee object
     * @return the {@link ApiResponse} with the updated Employee Wrapped in a {@link ResponseEntity}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        Employee updatedEmployee = this.employeeService.updateEmployee(id, employee);

        ApiResponse<Employee> response = ApiResponse.createSuccessResponse(HttpStatus.OK, updatedEmployee);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * delete an employee
     * @param id the ID of the employee that should be deleted
     * @return the {@link ApiResponse} with only the status code Wrapped in a {@link ResponseEntity}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEmployee(@PathVariable Long id) {
        this.employeeService.deleteEmployee(id);

        ApiResponse<String> response = ApiResponse.createSuccessResponse(HttpStatus.OK, null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
