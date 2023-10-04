package net.fribbtastic.learningSpringBoot.employee;

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
    public List<Employee> getAll() {

        List<Employee> employeeList = new ArrayList<>();

        this.repository.findAll().forEach(employeeList::add);

        return employeeList;
    }
}
