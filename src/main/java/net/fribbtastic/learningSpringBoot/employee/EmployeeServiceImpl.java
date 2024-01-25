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
}
