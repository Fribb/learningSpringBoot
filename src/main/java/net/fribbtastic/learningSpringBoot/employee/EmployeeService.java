package net.fribbtastic.learningSpringBoot.employee;

import java.util.List;

/**
 * @author Frederic Eßer
 */
public interface EmployeeService {

    List<Employee> getAllEmployees();
    Employee getEmployee(Long id);

    Employee createEmployee(Employee employee);
}
