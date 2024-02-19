package net.fribbtastic.learningSpringBoot.employee;

import net.fribbtastic.learningSpringBoot.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Frederic Eßer
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    /**
     * call the repository to get all employees and return them
     *
     * @return a list of Employees
     */
    @Override
    public List<Employee> getAllEmployees() {

        return new ArrayList<>(this.repository.findAll());      // call the repository to find all Employees
    }

    /**
     * call the repository to get a single employee by its ID
     * if the ID does not exist, an {@link EntityNotFoundException} will be thrown
     *
     * @param id - the unique ID of the employee
     * @return the Employee
     */
    @Override
    public Employee getEmployee(Long id) {
        return this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));     // find the Employee by its ID or, if it doesn't exist, throw an EntityNotFoundException
    }

    /**
     * call the repository to create a new employee and return it
     *
     * @param employee the information of the new employee that should be created
     * @return the created employee
     */
    @Override
    public Employee createEmployee(Employee employee) {
        return this.repository.save(employee);
    }

    /**
     * call the repository to update an existing employee by its ID
     *
     * @param id the ID of the employee
     * @param employee the new employee object
     * @return the updated employee Object
     */
    @Override
    public Employee updateEmployee(long id, Employee employee) {
        return this.repository.findById(id)
                .map(emp -> {
                    emp.setFirstName(employee.getFirstName());
                    emp.setLastName(employee.getLastName());
                    this.repository.save(emp);
                    return emp;
                })
                .orElseThrow(() -> new EntityNotFoundException(id));
    }
}
