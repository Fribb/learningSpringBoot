package net.fribbtastic.learningSpringBoot.employee;

import java.util.List;

/**
 * @author Frederic Eßer
 */
public interface EmployeeService {

    List<Employee> getAll();
    Employee getOne(Long id);
}
